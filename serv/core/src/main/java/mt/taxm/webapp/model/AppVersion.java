package mt.taxm.webapp.model;

import java.util.Date;

public class AppVersion {
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReleaseNotes() {
		return releaseNotes;
	}
	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getMustUpdate() {
		return mustUpdate;
	}
	public void setMustUpdate(int mustUpdate) {
		this.mustUpdate = mustUpdate;
	}
	private int id;
    private int num;
    private String title;
    private String releaseNotes;
    private String remark;
    private Date releaseDate;
    private String url;
    private int mustUpdate;

}
