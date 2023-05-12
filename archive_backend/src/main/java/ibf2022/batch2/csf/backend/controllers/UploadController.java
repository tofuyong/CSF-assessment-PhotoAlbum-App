package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Upload;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@RestController
@RequestMapping
public class UploadController {

	@Autowired
    private ImageRepository imageRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @CrossOrigin()
	public ResponseEntity<String> uploadForAngular(
        @RequestPart String name,
        @RequestPart String title,
        @RequestPart String comments,
		@RequestPart MultipartFile archive
    ) throws IOException{
		Upload uploadObj = new Upload();
		uploadObj.setName(name);
		uploadObj.setTitle(title);
		uploadObj.setComments(comments);
		// uploadObj.setArchive(archive.getBytes());
		
		/**
		 * save file to temp
		 */
		File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
		FileOutputStream o = new FileOutputStream(zip);
		IOUtils.copy(archive.getInputStream(), o);
		o.close();

		/**
		 * unizp file from temp by zip4j
		 */
		// String destination = "D:\\destination";
		String destination = ".\temp";
		try {
			try (ZipFile zipFile = new ZipFile(zip)) {
				zipFile.extractAll(destination);
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} finally {
			// delete temp file
			zip.delete();
		}
		
		/**  
		 * upload the unzipped files to S3
		 */
        File destinationDir = new File(destination);
		List<String> imageUrls = new ArrayList<>(); 
        for (File file : destinationDir.listFiles()) {
			String key = "";
			try {
				key = imageRepo.upload(file);
				String imageUrl = "https://tofuibfb22022.sgp1.digitaloceanspaces.com/myobjects/" + key; 
				imageUrls.add(imageUrl); 
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		uploadObj.setImageUrls(imageUrls);

		return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
	}
	


	// TODO: Task 5
	

	// TODO: Task 6

}


