SRC_DIR     = src/
TST_DIR     = test/
BIN_DIR     = bin/
SRC_BIN_DIR = $(BIN_DIR)$(SRC_DIR)
TST_BIN_DIR = $(BIN_DIR)$(TST_DIR)
SRC_CLS     = endgame.Main
TST_CLS     = endgame.MainTest
TST_LIB     = /usr/share/java/junit-4.12.jar:/usr/share/java/hamcrest-core.jar

default: clean build run

clean:
	rm -rf $(BIN_DIR)
build:
	mkdir -p $(BIN_DIR)
	find $(SRC_DIR) -name "*.java" | xargs javac -g -d $(SRC_BIN_DIR)
run:
	java -cp $(SRC_BIN_DIR) $(SRC_CLS)

test:	clean build
	find $(TST_DIR) -name "*.java" | xargs javac -g -d bin/test/ -cp $(SRC_BIN_DIR):$(TST_LIB)
	java -cp $(SRC_BIN_DIR):$(TST_BIN_DIR):$(TST_LIB) org.junit.runner.JUnitCore $(TST_CLS)

.PHONY: clean build run test
