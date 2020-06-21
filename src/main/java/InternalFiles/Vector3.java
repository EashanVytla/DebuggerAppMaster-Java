package InternalFiles;

public class Vector3 {
    public static final double EPSILON = 0.00001;

    private final double x, y;

    public Vector3(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector3 copy() {
        return new Vector3(x, y);
    }

    public Vector3 normalized() {
        double norm = norm();
        if (norm < EPSILON) {
            return new Vector3(1, 0);
        } else {
            return multiplied(1.0 / norm());
        }
    }

    public double norm() {
        return Math.hypot(x, y);
    }

    public Vector3 normalize(Vector3 other){
        double x = (this.x + other.x) / 2.0;
        double y = (this.y + other.y) / 2.0;

        return new Vector3(x, y);
    }

    public double dot(Vector3 other) {
        return x * other.x() + y * other.y();
    }

    public Vector3 multiplied(double scalar) {
        return new Vector3(scalar * x, scalar * y);
    }

    public Vector3 added(Vector3 other) {
        return new Vector3(x + other.x, y + other.y);
    }

    public Vector3 negated() {
        return new Vector3(-x, -y);
    }

    public Vector3 rotated(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new Vector3(newX, newY);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector3) {
            Vector3 otherVector = (Vector3) other;
            return Math.abs(x - otherVector.x) < EPSILON && Math.abs(y - otherVector.y) < EPSILON;
        }
        return false;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }

    public static double getCosAngle(Vector3 v1, Vector3 v2) {
        double dot = v1.x * v2.x + v1.y * v2.y;
        return dot / (v1.norm() * v2.norm());
    }

    public static double distance(Vector3 v1, Vector3 v2) {
        return v1.added(v2.negated()).norm();
    }
}
