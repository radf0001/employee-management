package edu.pucmm;


import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024  - 00:47
 */

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private Position juniorDeveloper;
    private Position seniorDeveloper;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        employeeManager = new EmployeeManager();
        juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
        seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
        employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
        employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
        employeeManager.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        // TODO: Agregar employee2 al employeeManager y verificar que se agregó correctamente.
        employeeManager.addEmployee(employee2);
        // - Verificar que el número total de empleados ahora es 2.
        assertEquals(2, employeeManager.getEmployees().size());
        // - Verificar que employee2 está en la lista de empleados.
        assertTrue(employeeManager.getEmployees().contains(employee2));
    }

    @Test
    public void testRemoveEmployee() {
        // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó correctamente.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Eliminar employee1 del employeeManager.
        employeeManager.removeEmployee(employee1);
        // - Verificar que el número total de empleados ahora es 1.
        assertEquals(1, employeeManager.getEmployees().size());
        // - Verificar que employee1 ya no está en la lista de empleados.
        assertFalse(employeeManager.getEmployees().contains(employee1));
    }

    @Test
    public void testCalculateTotalSalary() {
        // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario total.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Verificar que el salario total es la suma de los salarios de employee1 y employee2.
        assertEquals(employee2.getSalary() + employee1.getSalary(), employeeManager.calculateTotalSalary());
    }

    @Test
    public void testUpdateEmployeeSalaryValid() {
        // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la actualización.
        // - Actualizar el salario de employee1 a 45000.
        employeeManager.updateEmployeeSalary(employee1, 45000);
        // - Verificar que el salario de employee1 ahora es 45000.
        assertEquals(45000, employee1.getSalary());
    }

    @Test
    public void testUpdateEmployeeSalaryInvalid() {
        // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y verificar la excepción.
        // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del rango para Junior Developer).
        // - Verificar que se lanza una InvalidSalaryException.
        assertThrows(InvalidSalaryException.class, () -> {
            employeeManager.updateEmployeeSalary(employee1, 60000);
        });
    }

    @Test
    public void testUpdateEmployeeSalaryEmployeeNotFound() {
        // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar el salario de employee2 a 70000.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeManager.updateEmployeeSalary(employee2, 70000);
        });
    }

    @Test
    public void testUpdateEmployeePositionValid() {
        // TODO: Actualizar la posición de employee2 a una posición válida y verificar la actualización.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Actualizar la posición de employee2 a seniorDeveloper.
        employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
        // - Verificar que la posición de employee2 ahora es seniorDeveloper.
        assertEquals(seniorDeveloper, employee2.getPosition());
    }

    @Test
    public void testUpdateEmployeePositionInvalidDueToSalary() {
        // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y verificar la excepción.
        // - Intentar actualizar la posición de employee1 a seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException porque el salario de employee1 no está dentro del rango para Senior Developer.
        assertThrows(InvalidSalaryException.class, () -> {
            employeeManager.updateEmployeePosition(employee1, seniorDeveloper);
        });
    }

    @Test
    public void testUpdateEmployeePositionEmployeeNotFound() {
        // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar la posición de employee2 a juniorDeveloper.
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeManager.updateEmployeePosition(employee2, juniorDeveloper);
        });
    }

    @ParameterizedTest
    @ValueSource(doubles = {40000, 60000, 70000, 50000})
    public void testIsSalaryValidForPosition(double salario) {
        // TODO: Verificar la lógica de validación de salario para diferentes posiciones.
        // - Verificar que un salario de 40000 es válido para juniorDeveloper.
        if (salario == 40000) {
            Employee employee = new Employee("10", "Ramon", juniorDeveloper, salario);
            employeeManager.addEmployee(employee);
            assertTrue(employeeManager.getEmployees().contains(employee));
        }
        // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
        else if (salario == 60000) {
            Employee employee = new Employee("11", "Mario", juniorDeveloper, salario);
            assertThrows(InvalidSalaryException.class, () -> {
                employeeManager.addEmployee(employee);

            });
        }
        // - Verificar que un salario de 70000 es válido para seniorDeveloper.
        else if (salario == 70000) {
            Employee employee = new Employee("12", "Carlos", seniorDeveloper, salario);
            employeeManager.addEmployee(employee);
            assertTrue(employeeManager.getEmployees().contains(employee));
        }
        // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
        else if (salario == 50000) {
            Employee employee = new Employee("13", "Jose", seniorDeveloper, salario);
            assertThrows(InvalidSalaryException.class, () -> {
                employeeManager.addEmployee(employee);
            });
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {60000, 40000})
    public void testAddEmployeeWithInvalidSalary(double salario) {
        // TODO: Intentar agregar empleados con salarios inválidos y verificar las excepciones.
        // - Crear un empleado con un salario de 60000 para juniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        if (salario == 60000) {
            assertThrows(InvalidSalaryException.class, () -> {
                employeeManager.addEmployee(new Employee("20", "Joel", juniorDeveloper, salario));
            });
        }
        // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        else if (salario == 40000) {
            assertThrows(InvalidSalaryException.class, () -> {
                employeeManager.addEmployee(new Employee("21", "Juan", seniorDeveloper, salario));
            });
        }
    }

    @Test
    public void testRemoveExistentEmployee() {
        // TODO: Eliminar un empleado existente y verificar que no se lanza una excepción.
        // - Eliminar employee1 del employeeManager.
        // - Verificar que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> employeeManager.removeEmployee(employee1));
    }

    @Test
    public void testRemoveNonExistentEmployee() {
        // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
        // - Intentar eliminar employee2 (no agregado al manager).
        // - Verificar que se lanza una EmployeeNotFoundException.
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeManager.removeEmployee(employee2);
        });
    }

    @Test
    public void testAddDuplicateEmployee() {
        // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
        // - Intentar agregar employee1 nuevamente al employeeManager.
        // - Verificar que se lanza una DuplicateEmployeeException.
        assertThrows(DuplicateEmployeeException.class, () -> {
            employeeManager.addEmployee(employee1);
        });
    }
}