// GradeList creates an array of grades and provides methods to deploy on them

class GradeList{
    private double[] grades;
    public GradeList(){
        // By default, create a list with 30 elements
        this.grades = new double[30];
    }
    public GradeList(int size){
        // Create custom-sized list, catching negative parameter exception
        try{
            this.grades = new double[size];
        } catch (NegativeArraySizeException e){
            System.out.println("Exception: grades length must be positive. Setting grade length to 30.");
            this.grades = new double[30];
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
            this.grades[i] = newGradeVal;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Exception: grade assignment index out of bounds");
        }
    }
    public double getGrade(int i){
        // Get grade value of given index
        try{
            return this.grades[i];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Exception: grade request index out of bounds");
            return 0.0;
        }
    }
    public double getMax(){
        // Get highest grade in the list
        double max = 0.0;
        for (int i = 0; i < this.grades.length; i++){
            if (this.grades[i] > max){
                max = this.grades[i];
            }
        }
        return max;
    }
    public double average(){
        // Get the average of all grades
        double total = 0.0;
        for (int i = 0; i < this.grades.length; i++){
            total += this.grades[i];
        }
        return total / this.grades.length;
    }
    public void print_grades(){
        // Print all grades in index order
        System.out.println("Grades:");
        for (int i = 0; i < this.grades.length; i++){
            System.out.println("  " + this.grades[i]);
        }
    }
}
