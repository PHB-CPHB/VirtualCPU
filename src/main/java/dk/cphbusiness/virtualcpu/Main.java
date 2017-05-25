package dk.cphbusiness.virtualcpu;

public class Main {
  
  public static void main(String[] args) {
    System.out.println("Welcome to the awesome CPU program");
    //Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
    Program tail = new Program("01000010", "00010000", "01001010", "00010000", "00001100", "11001000", "00010010", 
            "00001111", "00110010", "00000111", "10001100", "00011001", "00110101", "00000010",
            "00100010", "00110010", "00010111", "00100001", "00001100", "10001000");
    Machine machine = new Machine();
    machine.load(tail);
    machine.print(System.out);
    while(true){
    machine.tick();
    machine.print(System.out);
    }

    //for (int line : program) System.out.println(">>> "+line);
    
    }
    
  }


/*

TFACT

MOV 1 A [01000010]
PUSH A [00010000]
MOV 5 A [01001010]
PUSH A [00010000]

ALWAYS [00001100]
CALL #8 (TFACT) [11001000]
POP A [00010010]
HALT [00001111]

MOV +1 A [00110010]
NZERO [00000111]
JMP #12 (RECUR) [10001100]
RTN +1 [00011001]

MOV +2 B [00110101]
MUL [00000010]
MOV A +2 [00100010]
MOV +1 A [00110010]
DEC [00010111]
MOV A +1 [00100001]
ALWAYS [00001100]
JMP #8 [10001000]
*/