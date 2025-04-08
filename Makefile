JAVAC = javac
JAVA = java
SRC_DIR = .
BIN_DIR = bin

SOURCES = $(SRC_DIR)/Calculator.java \
		  $(SRC_DIR)/CalculatorClient.java \
		  $(SRC_DIR)/CalculatorImplementation.java \
		  $(SRC_DIR)/CalculatorServer.java

CLASSES = $(BIN_DIR)/Calculator.class \
		  $(BIN_DIR)/CalculatorClient.class \
		  $(BIN_DIR)/CalculatorImplementation.class \
		  $(BIN_DIR)/CalculatorServer.class

SERVER_CLASS = CalculatorServer
CLIENT_CLASS = CalculatorClient

all: $(BIN_DIR) $(CLASSES)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) -d $(BIN_DIR) $<

server: all
	$(JAVA) -cp $(BIN_DIR) $(SERVER_CLASS)

client: all
	$(JAVA) -cp $(BIN_DIR) $(CLIENT_CLASS)

clean:
	rm -rf $(BIN_DIR)/*.class

.PHONY: all clean server client