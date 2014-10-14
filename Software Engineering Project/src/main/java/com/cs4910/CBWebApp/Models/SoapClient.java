package com.cs4910.CBWebApp.Models;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.danube.scrumworks.api.client.ScrumWorksEndpoint;
import com.danube.scrumworks.api.client.ScrumWorksEndpointBindingStub;
import com.danube.scrumworks.api.client.ScrumWorksServiceLocator;
import com.danube.scrumworks.api.client.types.BacklogItemWSO;
import com.danube.scrumworks.api.client.types.ProductWSO;
import com.danube.scrumworks.api.client.types.ReleaseWSO;
import com.danube.scrumworks.api.client.types.ServerException;
import com.danube.scrumworks.api.client.types.SprintWSO;

/**
 * SoapClient is a convenience class for calling Scrumworks web services.
 * 
 * @author bjwillia
 * 
 * Change History: <br>
 * 05/12/2014 bjwillia - updated for renaming Middleware product to Development Services. <br>
 * 10/16/2013 bjwillia - updated URL for update to 6.3 and new server. <br>
 */

public class SoapClient {
	protected URL url;

	protected ScrumWorksServiceLocator locator;

	protected ScrumWorksEndpoint endpoint;

	// protected ProductWSO product = new ProductWSO(DEFAULT_EFFORT_UNITS,
	// DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME);

	protected ProductWSO product;

	protected ProductWSO portalProduct;

	protected static String DEFAULT_URL = "http://swpro.cbsh.com/scrumworks-api/scrumworks";
	
	//old production version
	//protected static String DEFAULT_URL = "http://wktv1swpaa01.cbsh.com:8080/scrumworks-api/scrumworks";

	//test url for new version
	//protected static String DEFAULT_URL = "http://wktv1eefaa01.cbsh.com:8080/scrumworks-api/scrumworks";

	protected static String DEFAULT_USER = "Middleware";

	protected static String DEFAULT_PWD = "M1zzouRah";

	protected static String DEFAULT_EFFORT_UNITS = "Perfect Days";

	protected static Long DEFAULT_PRODUCT_ID = new Long(-162609799794753328l);

	protected static String DEFAULT_PRODUCT_NAME = "Development Services";
	//protected static String DEFAULT_PRODUCT_NAME = "Middleware";

	protected static Long PORTAL_PRODUCT_ID = new Long(424077187262293718l);

	protected static String PORTAL_EFFORT_UNITS = "Story Points";

	protected static String PORTAL_PRODUCT_NAME = "Portal";

	protected ArrayList sprints = new ArrayList<SprintWSO>();

	public SoapClient() {
		super();
		initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoapClient client = new SoapClient();
			WfmProduct wfmProduct = null;
			if (args.length == 0) {
				wfmProduct = client.getWfmProduct();
			} else {
				wfmProduct = client.getWfmPortalProduct();
			}
			client.getSprints(wfmProduct);
			System.out.println(wfmProduct.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void initialize() {
		try {
			setUrl(new URL(DEFAULT_URL));
			setLocator(new ScrumWorksServiceLocator());
			setEndpoint(locator.getScrumWorksEndpointPort(url));
			((ScrumWorksEndpointBindingStub) getEndpoint())
					.setUsername(DEFAULT_USER);
			((ScrumWorksEndpointBindingStub) getEndpoint())
					.setPassword(DEFAULT_PWD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public WfmProduct getWfmProduct() throws Exception {
		return getWfmProduct(getProduct());
	}

	public WfmProduct getWfmPortalProduct() throws Exception {
		return getWfmProduct(getPortalProduct());
	}

	public WfmProduct getWfmProduct(String effortUnits, Long productId,
			String productName) throws Exception {
		ProductWSO productWSO = new ProductWSO();
		productWSO.setId(productId);
		productWSO.setName(productName);
		productWSO.setEffortUnits(effortUnits);
		return getWfmProduct(productWSO);
	}

	public WfmProduct getWfmProduct(ProductWSO productWSO) throws Exception {
		ArrayList<ReleaseWSO> releases = new ArrayList(Arrays
				.asList(getEndpoint().getReleases(productWSO)));
		ArrayList<WfmRelease> wfmReleases = new ArrayList();
		Iterator<ReleaseWSO> iReleases = releases.iterator();
		while (iReleases.hasNext()) {
			WfmRelease wfmRelease = new WfmRelease(iReleases.next());
			wfmReleases.add(wfmRelease);
		}
		WfmProduct wfmProduct = new WfmProduct(productWSO, wfmReleases);
		ArrayList<BacklogItemWSO> backlogItems = new ArrayList(Arrays
				.asList(getEndpoint().getActiveBacklogItems(productWSO)));
		Iterator<BacklogItemWSO> iBacklogItems = backlogItems.iterator();
		while (iBacklogItems.hasNext()) {
			BacklogItemWSO backlogItem = iBacklogItems.next();
			// Only include those backlog items that have not been completed
			if (backlogItem.getCompletedDate() == null) {
				WfmRelease release = wfmProduct.getRelease(backlogItem
						.getReleaseId());
				release.addBacklogItem(backlogItem);
			}
		}
		// System.out.println("Product effort: " + wfmProduct.getEffort());
		/*
		 * Iterator<WfmRelease> iReleases2 =
		 * wfmProduct.getReleases().iterator(); while (iReleases2.hasNext()) {
		 * WfmRelease release = iReleases2.next(); int effort =
		 * release.getEffort(); if (effort > 0) {
		 * System.out.println(release.getEffort() + " is the effort for Release: " +
		 * release.getRelease().getTitle()); Iterator<BacklogItemWSO>
		 * iBacklogItems2 = release.getBacklogItems().iterator(); while
		 * (iBacklogItems2.hasNext()) { BacklogItemWSO bItem =
		 * iBacklogItems2.next(); StringBuffer sb = new StringBuffer();
		 * sb.append(" Backlog Item Title: ").append(bItem.getTitle());
		 * sb.append(" - Story points: ").append(bItem.getEstimate());
		 * System.out.println(sb.toString()); } } }
		 * System.out.println("-----------------------------------------------");
		 */
		wfmProduct.setSprints(getSprints(wfmProduct));
		return wfmProduct;
	}

	public ScrumWorksEndpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(ScrumWorksEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public ScrumWorksServiceLocator getLocator() {
		return locator;
	}

	public void setLocator(ScrumWorksServiceLocator locator) {
		this.locator = locator;
	}

	public ProductWSO getProduct() {
		if (product == null) {
			ProductWSO productWSO = new ProductWSO();
			productWSO.setEffortUnits(DEFAULT_EFFORT_UNITS);
			productWSO.setId(DEFAULT_PRODUCT_ID);
			productWSO.setName(DEFAULT_PRODUCT_NAME);
			setProduct(productWSO);
		}
		return product;
	}

	public ProductWSO getPortalProduct() {
		if (portalProduct == null) {
			ProductWSO productWSO = new ProductWSO();
			productWSO.setEffortUnits(PORTAL_EFFORT_UNITS);
			productWSO.setId(PORTAL_PRODUCT_ID);
			productWSO.setName(PORTAL_PRODUCT_NAME);
			setPortalProduct(productWSO);
		}
		return portalProduct;
	}

	public void setProduct(ProductWSO product) {
		this.product = product;
	}

	public void setPortalProduct(ProductWSO portalProduct) {
		this.portalProduct = portalProduct;
	}

	public ArrayList<SprintWSO> getSprints(WfmProduct wfmProduct) {
		try {
			if (sprints == null || sprints.isEmpty()) {
				if (wfmProduct.getProduct().getName().equalsIgnoreCase(DEFAULT_PRODUCT_NAME)) {
					setSprints(new ArrayList<SprintWSO>(Arrays.asList(getEndpoint()
						.getSprints(getProduct()))));}
			else {
				setSprints(new ArrayList<SprintWSO>(Arrays.asList(getEndpoint()
						.getSprints(getPortalProduct()))));}
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return sprints;
	}

	public void setSprints(ArrayList<SprintWSO> sprints) {
		this.sprints = sprints;
	}

}
