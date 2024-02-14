import java.awt.*;

public abstract class Car implements Movable {
    protected int nrDoors; 
    protected double enginePower; 
    protected double currentSpeed;
    protected Color color; 
    protected String modelName; 

    // Add properties for tracking position and direction
    protected double x = 0;
    protected double y = 0;
    protected double direction = 0; // In degrees, let's say 0 degrees is facing 'east'

    public Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        stopEngine();
    }

    // Common Getters and Setters
    public int getNrDoors() { return nrDoors; }
    public double getEnginePower() { return enginePower; }
    public double getCurrentSpeed() { return currentSpeed; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    // Common behavior
    public void startEngine() { currentSpeed = 0.1; } 
    public void stopEngine() { currentSpeed = 0; }

    // Abstract method - Car-specific calculation
    protected abstract double speedFactor(); 

    // Common behaviors depending on abstract speedFactor
    public void incrementSpeed(double amount) { 
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }
    public void decrementSpeed(double amount) { 
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount, 0);
    }
    
    @Override
    public void move() {
        // Calculate movement based on speed and direction
        double radians = Math.toRadians(direction);
        x += currentSpeed * Math.cos(radians);
        y += currentSpeed * Math.sin(radians);
    }

    @Override
    public void turnLeft() {
        direction = (direction + 90) % 360; // Add 90, handle overflow 
    }

    @Override
    public void turnRight() {
        direction = (direction - 90) % 360; // Subtract 90, handle underflow
    }

    public void gas(double amount) {
        amount = Math.max(0, Math.min(amount, 1)); // Clamp input between 0 and 1

        double potentialSpeed = getCurrentSpeed() + speedFactor() * amount;
        currentSpeed = Math.min(potentialSpeed, enginePower); // Limit to engine power
    }

    public void brake(double amount) {
        amount = Math.max(0, Math.min(amount, 1)); // Clamp input between 0 and 1

        double potentialSpeed = getCurrentSpeed() - speedFactor() * amount;
        currentSpeed = Math.max(potentialSpeed, 0); // Limit to 0 (cannot go negative)
    } 
}