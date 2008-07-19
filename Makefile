JAVACOMPILER=javac

all:
	@export CLASSPASS=´pwd´
	make JAVACOMPILER=$(JAVACOMPILER) -C net/rheoli/Coffyjy/General
	make JAVACOMPILER=$(JAVACOMPILER) -C net/rheoli/Coffyjy/ConstantPool
	make JAVACOMPILER=$(JAVACOMPILER) -C net/rheoli/Coffyjy/DisAss
	make JAVACOMPILER=$(JAVACOMPILER) -C net/rheoli/Coffyjy

jar:
	@rm -f Coffyjy.jar
	jar cfm Coffyjy.jar Manifest.txt *

clean:
	make -C net/rheoli/Coffyjy/General clean
	make -C net/rheoli/Coffyjy/ConstantPool clean
	make -C net/rheoli/Coffyjy/DisAss clean
	make -C net/rheoli/Coffyjy clean
