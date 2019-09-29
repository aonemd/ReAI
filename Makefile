default: clean build run

clean:
	rm -rf bin/
build:
	mkdir -p bin/
	find src/ -name "*.java" | xargs javac -g -d bin/
run:
	java -cp bin/ endgame.Main
