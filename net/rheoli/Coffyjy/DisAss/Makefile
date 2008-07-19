all: DisAssException.class cException.class cDisAss.class 

cDisAss.class: cDisAss.java
	$(JAVACOMPILER) cDisAss.java

cException.class: cException.java
	$(JAVACOMPILER) cException.java

DisAssException.class: DisAssException.java
	$(JAVACOMPILER) DisAssException.java

clean:
	rm -f cDisAss.class cException.class DisAssException.class

