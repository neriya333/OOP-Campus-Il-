//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--------~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  ENUM  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~--------~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


/**
 * I learned that Math.PI is the usage
 * 
 * that each of enum options is basicly a class.
 * the simple use is to creaet a class and if == used for measring if two objects are of the same class.
 * exmple: look at those line, that prints false. 
 * 
    EqualSidedShapes p = EqualSidedShapes.TRIANGLE; 
    System.out.println(p==EqualSidedShapes.PENTAGON);
 * 
 * 
 * BUT Emun can hold more than only is the class the same.
 * as shown above having functions and initializer.
 */


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ START EX 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

enum Days{
    SUNDAY("OOP!"), MONDAY("NAND"), TUESDAY("Probability"),
    WEDNESDAY("OOP!"), THURSDAY("Algorithms"), FRIDAY("It's Friday!"),
    SHABBAS("Shabbat Hayom!"); 
    
    // each constant is made once and calls the constructor
    
    Days(String dayClass) // constructor for all the constants
    {
        this.dayClass = dayClass;
    }
    private String dayClass; // private member
    public String getClass(){ // public getter for the member
        return dayClass;
    }
}

class Main{
    public static void main(String[] args){
        for(Days day : Days.values()){ // for each loop
            System.out.println(day.get_class());
        }
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ END OF EX 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


public class ShapeOperator {


    public void printShapeEdges(EqualSidedShapes shape){
        System.out.println("shape " + shape + " has " 
        + shape.getNumOfEdges() + " edges");
    }
    
    /*add your getShapePerimeter method here*/
    private double getShapePerimeter(EqualSidedShapes shape, int edgeLength){
        if (shape==EqualSidedShapes.TRIANGLE){return 3*edgeLength;}
        if (shape==EqualSidedShapes.SQUARE){return 4*edgeLength;}
        else {return 5*edgeLength;}
    }
    
    
    public static void main(String[] args) {
        ShapeOperator calculator = new ShapeOperator();
        for(EqualSidedShapes shape: EqualSidedShapes.values()){
            calculator.printShapeEdges(shape);
            System.out.println(calculator.getShapePerimeter(shape, 2));
        }
       
    }
}

enum EqualSidedShapes{
    /* Declare your enum constants, field constructor and getter*/
    TRIANGLE(3), 
    SQUARE(4), 
    PENTAGON(5);
    
    private final int numEdges;
    
    EqualSidedShapes(int edgeNum){
        this.numEdges = edgeNum;
    }
    
    public int getNumOfEdges(){return this.numEdges;}
    
}
