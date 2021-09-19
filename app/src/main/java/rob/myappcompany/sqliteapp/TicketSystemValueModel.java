package rob.myappcompany.sqliteapp;

public class TicketSystemValueModel {
    private int id;
    private String creator_name;
    private String question_ticket;
    private String title_created;
    private String situation_ticket;
    private String ticket_name_ticket;
    private String priority_ticket;
    private String created_date;
    private String updated_date;


    public TicketSystemValueModel(String creator_name, String question_ticket, String title_created, String situation_ticket, String ticket_name_ticket , String priority_ticket, String created_date, String updated_date) {
        this.id = id;
        this.creator_name = creator_name;
        this.question_ticket = question_ticket;
        this.title_created = title_created;
        this.situation_ticket = situation_ticket;
        this.ticket_name_ticket=ticket_name_ticket;
        this.priority_ticket = priority_ticket;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public TicketSystemValueModel() {
    }

    @Override
    public String toString() {
        return "TicketModel{" +
                "id=" + id +
                ", creator_name='" + creator_name + '\'' +
                ", question_ticket='" + question_ticket + '\'' +
                ", title_created='" + title_created + '\'' +
                ", situation_ticket='" + situation_ticket + '\'' +
                ", priority_ticket='" + priority_ticket + '\'' +
                ", created_date='" + created_date + '\'' +
                ", updated_date='" + updated_date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getQuestion_ticket() {
        return question_ticket;
    }

    public void setQuestion_ticket(String question_ticket) {
        this.question_ticket = question_ticket;
    }

    public String getTitle_created() {
        return title_created;
    }

    public void setTitle_created(String title_created) {
        this.title_created = title_created;
    }

    public String getSituation_ticket() {
        return situation_ticket;
    }

    public void setSituation_ticket(String situation_ticket) {
        this.situation_ticket = situation_ticket;
    }
    public String getTicket_name_ticket() {
        return ticket_name_ticket;
    }

    public void setTicket_name_ticket(String ticket_name_ticket) {
        this.ticket_name_ticket = ticket_name_ticket;
    }

    public String getPriority_ticket() {
        return priority_ticket;
    }

    public void setPriority_ticket(String priority_ticket) {
        this.priority_ticket = priority_ticket;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

}
