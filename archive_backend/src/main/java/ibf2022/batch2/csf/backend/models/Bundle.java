package ibf2022.batch2.csf.backend.models;
import java.util.Date;
import java.util.List;

public class Bundle {
    private String bundleId;
    private Date date;
    private String title;
    private String name;
    private String comments;
    private List<String> imageUrls;

    public String getBundleId() {return this.bundleId;}
    public void setBundleId(String bundleId) {this.bundleId = bundleId;}

    public Date getDate() {return this.date;}
    public void setDate(Date date) {this.date = date;}

    public String getTitle() {return this.title;}
    public void setTitle(String title) { this.title = title;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getComments() {return this.comments;}
    public void setComments(String comments) { this.comments = comments;}

    public List<String> getImageUrls() {return this.imageUrls;}
    public void setImageUrls(List<String> imageUrls) {this.imageUrls = imageUrls;}
    
}
