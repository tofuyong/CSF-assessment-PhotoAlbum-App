package ibf2022.batch2.csf.backend.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Bundle;

@Repository
public class ArchiveRepository {

	@Autowired
    private MongoTemplate mongoTemplate;
	
	private static final String ARCHIVES_COL = "archives";

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	/*
	 * db.archives.insertOne({
		bundleId: "123",
		date: new Date(),
		title: "Holiday",
		name: "Andrea",
		comments: "Had an amazing time",
		imageUrls: ["url1.jpeg", "url2.png", "url3.gif"]
		});
	 */
	public Bundle recordBundle(Bundle bundleObj) {
		return this.mongoTemplate.insert(bundleObj, ARCHIVES_COL);
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	/* 
		db.archives.findOne({ bundleId: "a9c7d10f" }) 
	*/
	public Bundle getBundleByBundleId(String bundleId) {
		Criteria criteria = Criteria.where("bundleId").is(bundleId);
		Query query = Query.query(criteria);
		Bundle bundle = mongoTemplate.findOne(query, Bundle.class, ARCHIVES_COL);
		return bundle;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	/* 
		db.archives.find().sort({ date: -1, title: -1 })
	*/
	public List<Bundle> getBundles(/* any number of parameters here */) {
		Query query = new Query().with(Sort.by(Sort.Direction.DESC, "date", "title"));
        return mongoTemplate.find(query, Bundle.class, ARCHIVES_COL);
	}

}
