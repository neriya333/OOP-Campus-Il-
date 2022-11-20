// in Person file
public class Person implements Talkable { 
    
    private String sentence;
    
    public Person(String sentence) {
        this.sentence = sentence;
    }
    
    public String talk(){
        return sentence;
    }
    //implement talk method here
}

// in Talkable file: implement your interface here
interface Talkable{
    public String talk(); 
}
