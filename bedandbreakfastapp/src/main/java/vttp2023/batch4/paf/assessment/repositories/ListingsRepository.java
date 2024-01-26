package vttp2023.batch4.paf.assessment.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.util.Duration;
import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
	 * db.listings.aggregate([
			{$match: {
				"address.suburb" :{$nin: [null,""," "]}
			}},
			{$group: { _id:"$address.suburb" }},
			{$sort:{"address.suburb":-1, _id: 1}}
		]);
	 *
	 *
	 */
	public List<String> getSuburbs(String country) {

		return template.findDistinct(new Query(Criteria.where("address.suburb").nin("", null)), "address.suburb", "listings", String.class);
		
    }



	/*
	 * 
	 *	db.listings.find(
			{
				"address.suburb": {$regex:'monterey', $options: 'i'},
				price:{$lte: 50},
				accommodates:2,
				min_nights:{$gte:1}
					}, 
			{ 
				 _id:1,name:1, accommodates:1,price:1
					}
			).sort({price:-1})
	 *
	 */

	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		Query query = new Query(Criteria.where("address.suburb").is(suburb).and("price").lte(priceRange).and("accommodates").gte(persons).and("min_nights").lte(duration));

		List<Document> results = template.find(query, Document.class, "listings");
		List<AccommodationSummary> listings = new LinkedList<>();

		for (Document d : results) {
			AccommodationSummary listing = new AccommodationSummary();
			listing.setId(d.getString("_id"));
			listing.setName(d.getString("name"));
			listing.setPrice(d.get("price", Number.class).floatValue());
			listing.setAccomodates(d.getInteger("accommodates"));
			listings.add(listing);
		}

		return listings;
		 
    	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
