// GradeArrayList creates an ArrayList of grades and provides methods to deploy on them
import java.util.ArrayList;

class GradeArrayList{
    private ArrayList<Double> grades;
    public GradeArrayList(){
        // By default, creates empty ArrayList
        this.grades = new ArrayList<Double>();
    }
    public GradeArrayList(int size){
        // Create custom-sized list, catching negative parameter exception
        try{
            this.grades = new ArrayList<Double>(size);
            for (int i = 0; i < size; i++){
                this.grades.add(0.0);
            }
        } catch (IllegalArgumentException e){
            System.out.println("Exception: incorrect grade length. Setting grade length to 0.");
            this.grades = new ArrayList<Double>();
        }
    }
    public void setGrade(int i, double newGradeVal){
        // Assign a grade value to an index, catching exceptions
        if (i < 0){
            System.out.println("Exception: must assign grade to positive index");
            return;
        }
        if ((newGradeVal < 0.0) || (newGradeVal > 100.0)){
            System.out.println("Exception: grades must be between 0 and 100");
            return;
        }
        try{
            this.grades.set(i, newGradeVal);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Exception: grade assignment index out of bounds");
        }
    }
    public void addGrade(double newGradeVal){
        if ((newGradeVal < 0.0) || (newGradeVal > 100.0)){
            System.out.println("Exception: grades must be between 0 and 100");
            return;
        }
        this.grades.add(newGradeVal);
    }
    public double getGrade(int i){
        // Get grade value of given index
        try{
            return this.grades.get(i);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Exception: grade request index out of bounds");
            return 0.0;
        }
    }
    public double getMax(){
        // Get highest grade in the list
        double max = 0.0;
        for (int i = 0; i < this.grades.size(); i++){
            double grade = this.grades.get(i);
            if (grade > max){
                max = grade;
            }
        }
        return max;
    }
    public double average(){
        // Get the average of all grades
        double total = 0.0;
        for (int i = 0; i < this.grades.size(); i++){
            total += this.grades.get(i);
        }
        // Round to 2 decimals
        double mean = total / this.grades.size();
        return (Math.round(mean*100.0)/100.0);
    }
    public void print_grades(){
        // Handle if no grades
        if (this.grades.size() == 0){
            System.out.println("No grades to display");
            return;
        }
        // Print all grades in index order
        System.out.println("Grades:");
        for (int i = 0; i < this.grades.size(); i++){
            System.out.println("  " + this.grades.get(i));
        }
    }
    public static void main(String[] args){
        GradeArrayList myGrades = new GradeArrayList();
        myGrades.addGrade(30);
        myGrades.addGrade(75);
        myGrades.addGrade(98.0);
        myGrades.print_grades();
        System.out.println("Second grade: " + myGrades.getGrade(1));
        System.out.println("Max: " + myGrades.getMax());
        System.out.println("Average: " + myGrades.average());
        System.out.println("Grades: " + myGrades.grades);
    }
}
