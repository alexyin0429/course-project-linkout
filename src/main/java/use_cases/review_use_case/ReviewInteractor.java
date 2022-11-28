package use_cases.review_use_case;
import Gateway.UserGateway;
import entities.*;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReviewInteractor implements ReviewInputBoundary{
    private final ReviewOutputBoundary outputBoundary;
    private final ReviewGateway reviewGateway;
    private final UserGateway userGateway;



    /**
     * Initialize a ReviewInteractor
     * @param outputBoundary a ReviewOutputBoundary object
     * @param reviewGateway a ReviewGateway object
     * @param userGateway a UserGateway object

     */

    public ReviewInteractor(ReviewOutputBoundary outputBoundary, ReviewGateway reviewGateway, UserGateway userGateway) {
        this.outputBoundary = outputBoundary;
        this.reviewGateway = reviewGateway;
        this.userGateway = userGateway;
    }


    /**
     * this method adds the review object to the receiver's review list, save the review to the database and return a
     * ReviewResponseModel object which includes the content of the review and the status of the review.
     *
     * @param review a ReviewRequestModel object gathering the input info (rating, comment, writer's and receiver's accountname)
     * @return a ReviewResponseModel object which includes the content of the review and the status of the review (added, deleted, hided)
     */
    @Override
    public ReviewResponseModel addReview(ReviewRequestModel review) throws IOException, InvalidAttributeValueException {
        User receiver = userGateway.findUser(review.getReceiver());
        Review reviewObject = new Review(review.getRating(), review.getComment(), review.getWriter(), review.getReceiver());
        receiver.addReviews(reviewObject);
        reviewGateway.saveReview(reviewObject);
        String reviewString = "Review:\n" + "Comment: " + review.getComment() + "\n" +
                "Rating: " + review.getRating() + "\n" + "Writer: " + review.getWriter() + "\n" +
                "Receiver: " + review.getReceiver();
        LocalDateTime now = LocalDateTime.now();
        ReviewResponseModel response = new ReviewResponseModel(reviewString, "added", now.toString());
        return outputBoundary.reportReview(response);

    }

    /**
     * this method deletes the review object from the receiver's review list, move the review from the database and return a
     * ReviewResponseModel object which includes the content of the review and the status of the review (deleted).
     *
     * @param id ID of the review
     * @return a ReviewResponseModel object which includes the content of the review and the status of the review (added, deleted, hided)
     */
    @Override
    public ReviewResponseModel deleteReview(int id) throws IOException, InvalidAttributeValueException {
        Review review = reviewGateway.findReview(id);
        String receivername = review.getReceiver();
        User receiver = userGateway.findUser(receivername);
        receiver.deleteReview(id);
        reviewGateway.removeReview(id, receivername);
        LocalDateTime now = LocalDateTime.now();
        ReviewResponseModel response = new ReviewResponseModel("deleted", now.toString());
        return outputBoundary.reportReview(response);
    }

    /**
     * this method hides the review object from the receiver's review list, do not delte the review from the database
     * and return a ReviewResponseModel object which includes the content of the review and the status of the
     * review (hided).
     *
     * @param id ID of the review
     * @return a ReviewResponseModel object which includes the content of the review and the status of the review (added, deleted, hided)
     */
    @Override
    public ReviewResponseModel hideReview(int id) throws IOException, InvalidAttributeValueException {
        Review review = reviewGateway.findReview(id);
        String receivername = review.getReceiver();
        User receiver = userGateway.findUser(receivername);
        // TODO: manipulate view here?
        LocalDateTime now = LocalDateTime.now();
        ReviewResponseModel response = new ReviewResponseModel("hided", now.toString());
        return outputBoundary.reportReview(response);
    }

}