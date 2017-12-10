# TeX2Hwp

### What is this?
A simple TeX-to-Hwp converter written in Java.
This project is just a proof-of-concept. It supports just a few
TeX math commands now.

(Project for KAIST CS495 'Individual research')

### Requirements
- JDK (Recommended : 1.7 ~)

### Libraries I used
- [JEuclid](http://jeuclid.sourceforge.net/)
- [SnuggleTeX](http://www2.ph.ed.ac.uk/snuggletex/documentation/overview-and-features.html)

### Screenshots
![TeX2Hwp](http://i63.tinypic.com/30mb7ld.png)

### How to use the core module
Instead of running the ```.jar``` file directly, you can write a
TeX-to-Hwp converter using the classes in ```core```.
For example, we can write a simple app like the following:

##### Source code (```Demo.java```)
```java
import org.w3c.dom.Node;

import tex2hwp.core.TeXParser;
import tex2hwp.core.HwpConverter;

public class Demo {
    public static void main(String[] args) throws Exception {
        TeXParser parser = new TeXParser();
        HwpConverter converter = new HwpConverter();

        // TeX -> MathML
        String tex = "\\frac{1}{2} + 3 = \\frac{7}{2}";
        parser.parse("\\[" + tex + "\\]"); // Warning: No return value!

        // MathML -> Hwp
        Node ml = parser.getMlDom();
        String hwp = converter.convertNode(ml);

        System.out.println("TeX - " + tex);
        System.out.println("Hwp - " + hwp);
    }
}
```

##### Output
```
TeX - \frac{1}{2} + 3 = \frac{7}{2}
Hwp - { 1 } over { 2 } + 3 = { 7 } over { 2 }
```
