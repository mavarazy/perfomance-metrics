public class Test {

    public static void main (String args[])
    {
    String a, b, c, d;
    a = "Hello1234";
    b = "Hello" + String.valueOf(1234);
    c = "Hello" + "1234";       
    d = new String (new char[]{'H', 'e', 'l', 'l', 'o', '1', '2', '3', '4'});
    System.out.print (a == b);
    System.out.print (" ");
    System.out.print (a.equals(b));
    System.out.print (" ");
    System.out.print (a == c);
    System.out.print (" ");
    System.out.print (a.equals(c));
    System.out.print (" ");
    System.out.print (a == d);
    System.out.print (" ");
    System.out.print (a.equals(d));
    System.out.print (" ");
    }
}
