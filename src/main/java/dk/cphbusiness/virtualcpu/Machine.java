package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {

    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() {
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            System.out.println("NOP");
            // 0000 0000  NOP
            cpu.incIp();
            // cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            System.out.println("ADD");
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0010) {
            System.out.println("MUL");
            // 0000 0010
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0011) {
            System.out.println("DIV");
            // 0000 0011
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0100) {
            System.out.println("ZERO");
            // 0000 0100
            cpu.setFlag(cpu.getA() == 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0101) {
            System.out.println("NEG");
            // 0000 0101
            cpu.setFlag(cpu.getA() < 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0110) {
            System.out.println("POS");
            // 0000 0110
            cpu.setFlag(cpu.getA() > 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0111) {
            System.out.println("NZERO");
            // 0000 0111
            cpu.setFlag(cpu.getA() != 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1000) {
            System.out.println("EQ");
            // 0000 1000
            cpu.setFlag(cpu.getA() == cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1001) {
            System.out.println("LT");
            // 0000 1001
            cpu.setFlag(cpu.getA() < cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1010) {
            System.out.println("GT");
            // 0000 1010
            cpu.setFlag(cpu.getA() > cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1011) {
            System.out.println("NEQ");
            // 0000 1011
            cpu.setFlag(cpu.getA() != cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1100) {
            System.out.println("ALWAYS");
            // 0000 1100
            cpu.setFlag(true);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1101) {
            //Undefined
        } else if (instr == 0b0000_1110) {
            //Undefined
        } else if (instr == 0b0000_1111) {
            System.out.println("HALT");
            // 0000 1111
        } else if ((instr & 0b0001_0000) == 0b0001_0000) {
            System.out.println("PUSH r");
            // 0001 000r
            int r = (instr & 0b0000_0001);
            cpu.decSp();
            if (r == cpu.A) {
                memory.set(cpu.getSp(), cpu.getA());
            } else {
                memory.set(cpu.getSp(), cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        } else if ((instr & 0b0001_0010) == 0b0001_0010) {
            System.out.println("POP r");
            // 0001 001r
            int r = (instr & 0b0000_0010);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp()));
            } else {
                cpu.setB(memory.get(cpu.getSp()));
            }
            cpu.incSp();
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0001_0100) {
            System.out.println("MOV A B");
        } else if (instr == 0b0001_0101) {
            System.out.println("MOV B A");
        } else if (instr == 0b0001_0110) {
            System.out.println("INC");
        } else if (instr == 0b0001_0111) {
            System.out.println("DEC");
        } else if ((instr & 0b0001_1000) == 0b0001_1000) {
            System.out.println("RTN +o");
        } else if ((instr & 0b1111_0000) == 0b0010_0000) {
            System.out.println("MOV r o");
            // 0010 rooo
            int o = instr & 0b0000_0111;
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++

            // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
            // 00101011 finding instruction
            //    and
            // 11110000
            // --------
            // 00100000
            // 00101011 finding offset
            //    and
            // 00000111
            // --------
            // 00000011 = 3
            // 00101011 finding register
            //    and
            // 00001000
            // --------
            // 00001000 = 8
            //    >> 3
            // 00000001 = 1
        }
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

}
