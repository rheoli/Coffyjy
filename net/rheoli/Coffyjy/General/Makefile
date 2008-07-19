all: LabelException.class cLabelMgr.class cRuntimeConstants.class

cLabelMgr.class: cLabelMgr.java
	$(JAVACOMPILER) cLabelMgr.java

LabelException.class: LabelException.java
	$(JAVACOMPILER) LabelException.java

cRuntimeConstants.class: cRuntimeConstants.java
	$(JAVACOMPILER) cRuntimeConstants.java

clean:
	rm -f cLabelMgr.class LabelException.class cRuntimeConstants.class

