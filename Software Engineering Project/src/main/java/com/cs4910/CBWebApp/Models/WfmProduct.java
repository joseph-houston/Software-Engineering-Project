package com.cs4910.CBWebApp.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.danube.scrumworks.api.client.types.BacklogItemWSO;
import com.danube.scrumworks.api.client.types.ProductWSO;
import com.danube.scrumworks.api.client.types.SprintWSO;
import com.danube.scrumworks.api.client.types.ThemeWSO;

/**
 * Wrapper class for the ProductWSO code-generated class. This wrapper class
 * adds the release instances and provides convenience methods, such as
 * getEffort.
 * 
 * @author bjwillia
 * 
 * Change History: <br>
 * 06/21/2011 bjwillia - Added themes to the getBacklogItemsForReleasesString method. <br>
 */

public class WfmProduct {
	protected ProductWSO product = null;

	protected Collection<WfmRelease> releases = new ArrayList<WfmRelease>();
	protected Collection<SprintWSO> sprints = new ArrayList<SprintWSO>();

	public WfmProduct(ProductWSO product, Collection<WfmRelease> releases) {
		super();
		this.product = product;
		this.releases = releases;
	}

	public WfmProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WfmRelease getRelease(Long releaseId) {
		Iterator<WfmRelease> i = getReleases().iterator();
		while (i.hasNext()) {
			WfmRelease release = i.next();
			if (release.getRelease().getId().equals(releaseId)) {
				return release;
			}
		}
		return null;
	}

	public int getEffort() {
		int effort = 0;
		Iterator<WfmRelease> i = getReleases().iterator();
		while (i.hasNext()) {
			WfmRelease release = i.next();
			effort += release.getEffort();
		}
		return effort;
	}

	public String getReleasesWithMissingEffortsString() {
		StringBuffer sb = new StringBuffer();
		Iterator<WfmRelease> releases = getReleasesWithMissingEfforts()
				.iterator();
		while (releases.hasNext()) {
			WfmRelease release = releases.next();
			sb.append("Release: ").append(release.getRelease().getTitle())
					.append("\n");
			sb.append(release.getBacklogItemsMissingEffortsString());
			if (releases.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String getReleasesWithMissingDatesString() {
		StringBuffer sb = new StringBuffer();
		Iterator<WfmRelease> releases = getReleasesWithMissingDates()
				.iterator();
		while (releases.hasNext()) {
			sb.append("Release: ").append(
					releases.next().getRelease().getTitle());
			if (releases.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String getReleasesWithNoBacklogItemsString() {
		StringBuffer sb = new StringBuffer();
		Iterator<WfmRelease> releases = getReleasesWithNoBacklogItems()
				.iterator();
		while (releases.hasNext()) {
			WfmRelease release = releases.next();
			sb.append("Release: ").append(release.getRelease().getTitle());
			if (releases.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String getBacklogItemsForReleasesString() {
		StringBuffer sb = new StringBuffer();
		Iterator<WfmRelease> releases = getReleases().iterator();
		while (releases.hasNext()) {
			WfmRelease release = releases.next();
			int effort = release.getEffort();
			if (effort > 0) {
				sb.append("Release: ").append(release.getRelease().getTitle()).append("\n");
				sb.append("    Story Points: ").append(release.getEffort());
				Iterator<BacklogItemWSO> backlogItems = release
						.getBacklogItems().iterator();
				while (backlogItems.hasNext()) {
					BacklogItemWSO bItem = backlogItems.next();
					sb.append("\n").append("    Backlog Item: ").append(
							bItem.getTitle());
					sb.append("\n").append("        Themes: ").append(getThemes(bItem.getThemes()));
					sb.append("\n").append("        Story points: ").append(bItem.getEstimate());
					SprintWSO sprint = getSprint(bItem);
					if (sprint != null) {
						sb.append("\n").append("        sprint: ").append(sprint.getName());
					} else {
						sb.append("\n").append("        sprint: Uncommitted backlog item - not in a Sprint");
					}
				}
				if (releases.hasNext()) {
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}

	protected String getThemes(ThemeWSO[] themes) {
		StringBuffer sb = new StringBuffer();
		if (themes != null) {
			Iterator<ThemeWSO> themesI = (Arrays.asList(themes)).iterator();
			while (themesI.hasNext()) {
				ThemeWSO theme = themesI.next();
				sb.append(theme.getName());
				if (themesI.hasNext()) {
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}
	
	public SprintWSO getSprint(BacklogItemWSO backlogItem) {
		SprintWSO sprint = null;
		Iterator<SprintWSO> iSprints = getSprints().iterator();
		while (iSprints.hasNext()) {
			SprintWSO tSprint = iSprints.next();
			if (tSprint.getId().equals(backlogItem.getSprintId())) {
				sprint = tSprint;
				break;
			}
		}
		return sprint;
	}
	
	public ProductWSO getProduct() {
		return product;
	}

	public void setProduct(ProductWSO product) {
		this.product = product;
	}

	public Collection<WfmRelease> getReleases() {
		return releases;
	}

	public void setReleases(Collection<WfmRelease> releases) {
		this.releases = releases;
	}

	public Collection<WfmRelease> getReleasesWithMissingEfforts() {
		Collection<WfmRelease> releases = new ArrayList();
		Iterator<WfmRelease> i = getReleases().iterator();
		while (i.hasNext()) {
			WfmRelease release = i.next();
			if ((!release.getRelease().isArchived())
					&& (release.hasBacklogItemsWithMissingEfforts())) {
				releases.add(release);
			}
		}
		return releases;
	}

	public boolean hasReleasesWithMissingEfforts() {
		return !getReleasesWithMissingEfforts().isEmpty();
	}

	public Collection<WfmRelease> getReleasesWithMissingDates() {
		Collection<WfmRelease> releases = new ArrayList();
		Iterator<WfmRelease> i = getReleases().iterator();
		while (i.hasNext()) {
			WfmRelease release = i.next();
			if ((!release.getRelease().isArchived())
					&& (release.getRelease().getStartDate() == null || release
							.getRelease().getReleaseDate() == null)) {
				releases.add(release);
			}
		}
		return releases;
	}

	public boolean hasReleasesWithMissingDates() {
		return !getReleasesWithMissingDates().isEmpty();
	}

	public Collection<WfmRelease> getReleasesWithNoBacklogItems() {
		Collection<WfmRelease> releases = new ArrayList();
		Iterator<WfmRelease> i = getReleases().iterator();
		while (i.hasNext()) {
			WfmRelease release = i.next();
			if ((!release.getRelease().isArchived())
					&& release.getBacklogItems().isEmpty()) {
				releases.add(release);
			}
		}
		return releases;
	}

	public boolean hasReleasesWithNoBacklogItems() {
		return !getReleasesWithNoBacklogItems().isEmpty();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getProduct() != null) {
			sb.append("Product Name: ").append(getProduct().getName()).append(
					"\n");
			sb.append("Product id: ").append(getProduct().getId().longValue())
					.append("\n");
			String effortUnits = getProduct().getEffortUnits();
			sb.append("Effort Units: ").append(effortUnits).append("\n");
			sb.append("# of ").append(effortUnits).append(": ").append(
					getEffort()).append("\n");
			sb.append("-----------------------------------------------")
					.append("\n");
			if (hasReleasesWithMissingEfforts()) {
				sb.append("Missing efforts: ").append(true);
				sb
						.append("\n")
						.append(
								"Releases with un-estimated backlog item efforts are: ")
						.append("\n");
				sb.append(getReleasesWithMissingEffortsString()).append("\n");
			} else {
				sb.append("Missing efforts: ").append(false).append("\n");
			}
			sb.append("-----------------------------------------------")
					.append("\n");
			if (hasReleasesWithMissingDates()) {
				sb.append("Missing release dates: ").append(true);
				sb.append("\n").append("Releases with missing dates are: ")
						.append("\n");
				sb.append(getReleasesWithMissingDatesString()).append("\n");
			} else {
				sb.append("Missing release dates: ").append(false).append("\n");
			}
			sb.append("-----------------------------------------------")
					.append("\n");
			sb.append("Releases with no backlog items are: ").append("\n");
			sb.append(getReleasesWithNoBacklogItemsString()).append("\n");;
			sb.append("-----------------------------------------------")
					.append("\n");
			sb.append("Release backlog info: ").append("\n");
			sb.append(getBacklogItemsForReleasesString());
		} else {
			sb.append("No ProductWSO exists");
		}
		return sb.toString();
	}

	public Collection<SprintWSO> getSprints() {
		return sprints;
	}

	public void setSprints(Collection<SprintWSO> sprints) {
		this.sprints = sprints;
	}

}
