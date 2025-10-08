public class triangle {
    float side1, side2, side3;

    // Constructor
    public triangle(float s1, float s2, float s3) {
        side1 = s1;
        side2 = s2;
        side3 = s3;
    }

    public void checkTriangle (float side1, float side2, float side3) {
        if(side1 == side2 && side2 == side3) {
            System.out.println("The triangle is equilateral.");
        } else if(side1 == side2 || side2 == side3 || side1 == side3) {
            System.out.println("The triangle is isosceles.");
        } else {
            System.out.println("The triangle is scalene.");
        }
    }

    public static void main(String[] args) {
        triangle t1 = new triangle(3, 4, 5);
        t1.checkTriangle(t1.side1, t1.side2, t1.side3);

        triangle t2 = new triangle(5, 5, 5);
        t2.checkTriangle(t2.side1, t2.side2, t2.side3);
    }
}
