package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@RestController
@RequestMapping("/api")
public class UploadController {

	@Autowired
    private ImageRepository imageRepo;

	@Autowired
	private ArchiveRepository archiveRepo;

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
		Bundle bundleObj = new Bundle();
		bundleObj.setName(name);
		bundleObj.setTitle(title);
		bundleObj.setComments(comments);
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
		String destination = "./temp";
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
		bundleObj.setImageUrls(imageUrls);

		String bundleId = UUID.randomUUID().toString().substring(0, 8);
		bundleObj.setBundleId(bundleId);
		Date currentDate = new Date();
    	bundleObj.setDate(currentDate);

		try {
			archiveRepo.recordBundle(bundleObj);
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"bundleId\": \"" + bundleId + "\"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to record bundle");
		}

	}
	
	// TODO: Task 5
	@GetMapping(path="/{bundleId}")
	public ResponseEntity<Bundle> getBundle(@PathVariable String bundleId) {
        Bundle bundle = archiveRepo.getBundleByBundleId(bundleId);
		if (bundle != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(bundle);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null); 
		}
	}

	// TODO: Task 6
	@GetMapping(path="/all")
	public ResponseEntity<List<Bundle>> getAllBundles() {
        List<Bundle> bundles = archiveRepo.getBundles();
		if (bundles != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(bundles);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null); 
		}
	}

}


