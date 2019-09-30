SRC_DIR     = src/
BIN_DIR     = bin/
SRC_BIN_DIR = $(BIN_DIR)$(SRC_DIR)
SRC_CLS     = endgame.Main

default: clean build run

clean:
	rm -rf $(BIN_DIR)
build:
	mkdir -p $(BIN_DIR)
	find $(SRC_DIR) -name "*.java" | xargs javac -g -d $(SRC_BIN_DIR)
run:
	java -cp $(SRC_BIN_DIR) $(SRC_CLS)
