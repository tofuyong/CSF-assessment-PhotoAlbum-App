package ibf2022.batch2.csf.backend.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3Client;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// public Object upload(/* any number of parameters here */) {

	private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    public String upload(File file) throws IOException{

        // Check file size
        if (file.length() > MAX_FILE_SIZE) {
            throw new IOException("File is too large!");
        }

        // User data
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "Andrea");
        userData.put("uploadTime", new Date().toString());
        userData.put("originalFilename", file.getName());
        
        // Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(Files.probeContentType(file.toPath()));
        metadata.setContentLength(file.length());
        metadata.setUserMetadata(userData);
        String key = UUID.randomUUID().toString()
            .substring(0, 8);
        System.out.println(file.getName());
        
        StringTokenizer tk = new StringTokenizer(file.getName(), ".");
        int count = 0;
        String filenameExt = "";
        while(tk.hasMoreTokens()){
            if(count == 1){
                    filenameExt = tk.nextToken();
                break;
            }else{
                filenameExt = tk.nextToken();
            }
            count++;
        }
        System.out.println("myobjects/%s.%s".formatted(key, filenameExt));
        if(filenameExt.equals("blob"))
            filenameExt = filenameExt + ".png";
        PutObjectRequest putRequest = 
            new PutObjectRequest(
                "tofuibfb22022", 
                "myobjects/%s.%s".formatted(key, filenameExt), 
                new FileInputStream(file), 
                metadata);
        putRequest.withCannedAcl(
                CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);
        return "%s.%s".formatted(key, filenameExt);
	}
}
