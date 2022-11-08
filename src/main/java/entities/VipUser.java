package entities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;


public class VipUser extends User{

    private boolean isInvisible;
    private List<User> visitors;
    private Hashtable<Integer, List<Object>> hiddenReviews = new Hashtable<>();

    /**
     * Create a VIP user with its profile when isVIP status is true.
     *
     * @param profile the profile for this VIP user.
     * @param isVIP the VIP status for this VIP user.
     */


    public VipUser(Profile profile, boolean isVIP){
        super(profile, isVIP);
    }

    public void setInvisible(boolean arg){
        this.isInvisible = arg;
    }

    public List<User> showLikedMe(){
        return super.showLikedMe();
    }

    public List<User> showVisitor(){
        return this.visitors;
    }

    public void addVisitor(User visitor){
        this.visitors.add(visitor);
    }

    public void hideReview(ArrayList<Integer> review_ids){
        for (Integer id : review_ids){
            if (this.getReviews().containsKey(id)){
                hiddenReviews.put(id, this.getReviews().get(id));
            }
            else {
                throw new NoSuchElementException("Review " + id + "Not Found") ;
            }
        }
    }

}
