package gitlet;

import java.util.LinkedList;

public class MergeCommit extends Commit{

    private String given_branch;
    private int given_branch_size;
    private static final long serialVersionUID =  -7687123592477700924L;

    public void setGiven_branch(String s){
        this.given_branch=s;
    }
    MergeCommit(String msg, LinkedList<String> p){
        super(msg,p);
        given_branch_size=ComTreeControler.getCommitwithId(p.get(1)).getBranch_size();
    }
    public void printInfo(){
        System.out.println("===");
        System.out.println("commit "+getHash());
        System.out.println("Date: "+getTimeStamp());
        System.out.println(message());

        System.out.println("");

    }



}
