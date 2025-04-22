import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Double> results = new ArrayList<>();

        while (true) {
            int shapeChoice = askShapeMenu(input);
            if (shapeChoice == 0) break;

            int operationChoice = askOperationMenu(input);
            if (operationChoice == 0) continue;

            try {
                Operation operation = OperationFactory.create(shapeChoice, operationChoice, input);
                double result = operation.calculate();
                results.add(result);
                System.out.println("Resultado: " + result);
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
                input.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }
        }

        System.out.println("Resultados almacenados:");
        for (double value : results) {
            System.out.println(value);
        }
    }

    private static int askShapeMenu(Scanner input) {
        System.out.println("\nElige una figura:");
        System.out.println("1. Círculo");
        System.out.println("2. Cuadrado");
        System.out.println("3. Triángulo");
        System.out.println("4. Rectángulo");
        System.out.println("5. Pentágono");
        System.out.println("0. Salir");
        return readMenuOption(input, 5);
    }

    private static int askOperationMenu(Scanner input) {
        System.out.println("Elige una operación:");
        System.out.println("1. Área");
        System.out.println("2. Perímetro");
        System.out.println("3. Potencia");
        System.out.println("0. Volver");
        return readMenuOption(input, 3);
    }

    private static int readMenuOption(Scanner input, int max) {
        int option;
        while (true) {
            System.out.print("Opción: ");
            try {
                option = Integer.parseInt(input.nextLine().trim());
                if (option >= 0 && option <= max)
                    return option;
                System.out.println("Opción fuera de rango.");
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        }
    }

    public static double readPositiveDouble(Scanner input) {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(input.nextLine().trim());
                if (value > 0)
                    return value;
                System.out.println("El valor debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingresa un número válido.");
            }
        }
    }

    public static int readPositiveInt(Scanner input) {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(input.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Debe ser 0 o mayor.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }
}

interface Operation {
    double calculate();
}

class OperationFactory {
    public static Operation create(int shapeChoice, int operationChoice, Scanner input) {
        if (operationChoice == 3) {
            return new PowerOperation(input);
        }
        return switch (shapeChoice) {
            case 1 -> operationChoice == 1 ? new CircleArea(input) : new CirclePerimeter(input);
            case 2 -> operationChoice == 1 ? new SquareArea(input) : new SquarePerimeter(input);
            case 3 -> operationChoice == 1 ? new TriangleArea(input) : new TrianglePerimeter(input);
            case 4 -> operationChoice == 1 ? new RectangleArea(input) : new RectanglePerimeter(input);
            case 5 -> operationChoice == 1 ? new PentagonArea(input) : new PentagonPerimeter(input);
            default -> throw new IllegalArgumentException("Figura desconocida.");
        };
    }
}

class PowerOperation implements Operation {
    private final double base;
    private final int exponent;

    public PowerOperation(Scanner input) {
        System.out.println("Base:");
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Exponente (entero):");
        this.exponent = CalculatorApp.readPositiveInt(input);
    }

    @Override
    public double calculate() {
        return powerRecursive(base, exponent);
    }

    private double powerRecursive(double base, int exponent) {
        if (exponent == 0)
            return 1;
        return base * powerRecursive(base, exponent - 1);
    }
}

class CircleArea implements Operation {
    private final double radius;

    public CircleArea(Scanner input) {
        System.out.print("Radio: ");
        this.radius = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 3.1416 * radius * radius;
    }
}

class CirclePerimeter implements Operation {
    private final double radius;

    public CirclePerimeter(Scanner input) {
        System.out.println("Radio:");
        this.radius = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 2 * 3.1416 * radius;
    }
}

class SquareArea implements Operation {
    private final double side;

    public SquareArea(Scanner input) {
        System.out.println("Lado:");
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return side * side;
    }
}

class SquarePerimeter implements Operation {
    private final double side;

    public SquarePerimeter(Scanner input) {
        System.out.println("Lado:");
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 4 * side;
    }
}

class TriangleArea implements Operation {
    private final double base, height;

    public TriangleArea(Scanner input) {
        System.out.println("Base:");
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 0.5 * base * height;
    }
}

class TrianglePerimeter implements Operation {
    private final double a, b, c;

    public TrianglePerimeter(Scanner input) {
        System.out.println("Lado 1:");
        this.a = CalculatorApp.readPositiveDouble(input);
        System.out.println("Lado 2:");
        this.b = CalculatorApp.readPositiveDouble(input);
        System.out.println("Lado 3:");
        this.c = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return a + b + c;
    }
}

class RectangleArea implements Operation {
    private final double base, height;

    public RectangleArea(Scanner input) {
        System.out.println("Base:");
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return base * height;
    }
}

class RectanglePerimeter implements Operation {
    private final double base, height;

    public RectanglePerimeter(Scanner input) {
        System.out.println("Base:");
        this.base = CalculatorApp.readPositiveDouble(input);
        System.out.println("Altura:");
        this.height = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 2 * (base + height);
    }
}

class PentagonArea implements Operation {
    private final double side, apothem;

    public PentagonArea(Scanner input) {
        System.out.println("Lado:");
        this.side = CalculatorApp.readPositiveDouble(input);
        System.out.println("Apotema:");
        this.apothem = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return (5 * side * apothem) / 2;
    }
}

class PentagonPerimeter implements Operation {
    private final double side;

    public PentagonPerimeter(Scanner input) {
        System.out.println("Lado:");
        this.side = CalculatorApp.readPositiveDouble(input);
    }

    public double calculate() {
        return 5 * side;
    }
}