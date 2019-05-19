package ml.oopscpp.interweb;

public class Participant {

    private String participantImage;
    private String participantName;
    private String participantContact;
    private String participantAffiliation;
    private String participantAge;
    private String participantAbout;

    Participant(){
        this.participantImage = "";
        this.participantName = "";
        this.participantContact = "";
        this.participantAffiliation = "";
        this.participantAge = "";
        this.participantAbout = "";
    }

    Participant(String image,String name, String contact){
        this.participantImage = image;
        this.participantName = name;
        this.participantContact = contact;
        this.participantAffiliation = "affiliation";
        this.participantAge = "20";
        this.participantAbout = "about";
    }

    Participant(String image,String name, String contact,String affiliation,String age, String about){
        this.participantImage = image;
        this.participantName = name;
        this.participantContact = contact;
        this.participantAffiliation = affiliation;
        this.participantAge = age;
        this.participantAbout = about;
    }

    public String getParticipantImage() {
        return participantImage;
    }

    public String getParticipantName() {
        return participantName;
    }

    public String getParticipantContact() {
        return participantContact;
    }

    public String getParticipantAffiliation() {
        return participantAffiliation;
    }

    public String getParticipantAge() {
        return participantAge;
    }

    public String getParticipantAbout() {
        return participantAbout;
    }

}
