package com.cbi.scrumworks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.danube.scrumworks.api.client.types.BacklogItemWSO;
import com.danube.scrumworks.api.client.types.ReleaseWSO;

/**
 * Wrapper class for the ReleaseWSO code-generated class. This wrapper class
 * adds the backlogWSO instances and provides convenience methods, such as
 * getEffort.
 * 
 * @author bjwillia
 * 
 *         Change History: <br>
 *         08/27/2014 bjwillia - Added repeatingRelease boolean and short name. <br>
 */

public class WfmRelease {
	protected ReleaseWSO release = null;

	protected Collection<BacklogItemWSO> backlogItems = new ArrayList();
	protected String shortName = "";
	protected boolean repeatingRelease = false;

	public WfmRelease(ReleaseWSO release,
			Collection<BacklogItemWSO> backlogItems) {
		super();
		this.release = release;
		this.backlogItems = backlogItems;
		String title = release.getTitle();
		int idx = title.indexOf("-");
		if (title != null && idx != -1) {
			this.shortName = title.substring(0, idx).trim();
		}
	}

	public WfmRelease(ReleaseWSO release) {
		super();
		this.release = release;
		String title = release.getTitle();
		int idx = title.indexOf("-");
		if (title != null && idx != -1) {
			this.shortName = title.substring(0, idx).trim();
		}
	}

	public WfmRelease() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEffort() {
		getBacklogItemsMissingEfforts().clear();
		int effort = 0;
		Iterator<BacklogItemWSO> i = getBacklogItems().iterator();
		while (i.hasNext()) {
			BacklogItemWSO backlogItem = i.next();
			if (backlogItem.getEstimate() != null) {
				effort += backlogItem.getEstimate().intValue();
			} else {
				// System.out.println("missing estimate for Release: " +
				// getRelease().getTitle() + " backlog item: " +
				// backlogItem.getTitle());
			}
		}
		return effort;
	}

	public boolean hasBacklogItemsWithMissingEfforts() {
		return !getBacklogItemsMissingEfforts().isEmpty();
	}

	public String getBacklogItemsMissingEffortsString() {
		StringBuffer sb = new StringBuffer();
		Iterator<BacklogItemWSO> backlogItems = getBacklogItemsMissingEfforts()
				.iterator();
		while (backlogItems.hasNext()) {
			sb.append("    Backlog Item: ").append(
					backlogItems.next().getTitle());
			if (backlogItems.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public Collection<BacklogItemWSO> getBacklogItems() {
		return backlogItems;
	}

	public void setBacklogItems(Collection<BacklogItemWSO> backlogItems) {
		this.backlogItems = backlogItems;
	}

	public void addBacklogItem(BacklogItemWSO backlogItem) {
		getBacklogItems().add(backlogItem);
	}

	public ReleaseWSO getRelease() {
		return release;
	}

	public void setRelease(ReleaseWSO release) {
		this.release = release;
	}

	public Collection<BacklogItemWSO> getBacklogItemsMissingEfforts() {
		Collection<BacklogItemWSO> backlogItems = new ArrayList();
		Iterator<BacklogItemWSO> i = getBacklogItems().iterator();
		while (i.hasNext()) {
			BacklogItemWSO backlogItem = i.next();
			if (backlogItem.getEstimate() == null) {
				backlogItems.add(backlogItem);
			}
		}
		return backlogItems;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isRepeatingRelease() {
		return repeatingRelease;
	}

	public void setRepeatingRelease(boolean repeatingRelease) {
		this.repeatingRelease = repeatingRelease;
	}

}
