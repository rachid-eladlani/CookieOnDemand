package fr.unice.polytech.cookieFactory.users;

public class CC {
    private String name;
    private long no;
    private int yearIssue;
    private int monthIssue;
    private int cvv;
    private boolean save;

    public CC(String name, long no, int monthIssue, int yearIssue, int cvv, boolean save){
        this(name, no, monthIssue, yearIssue, cvv);
        this.save = save;
    }
    public CC(String name, long no, int monthIssue, int yearIssue, int cvv){
        this.name = name;
        this.no = no;
        this.yearIssue = yearIssue;
        this.monthIssue = monthIssue;
        this.cvv = cvv;
        this.save = false;
    }

    public boolean saveCC(){
        return save;
    }
}
