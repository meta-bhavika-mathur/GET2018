
public class Triangle
{
    public double area(double firstSide, double secondSide, double thirdSide)
    {
        // calculates area of triangle using heron's formula
        double s = (firstSide + secondSide + thirdSide) / 2;
        double area = Math.sqrt(s * (s - firstSide) * (s - secondSide) * (s - thirdSide));
        return area;
    }
}
