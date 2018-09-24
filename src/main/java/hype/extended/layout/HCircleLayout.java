package hype.extended.layout;

import hype.interfaces.HLayout;
import hype.H;
import hype.HDrawable;

import processing.core.PVector;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PApplet.radians;
import static processing.core.PApplet.map;

public class HCircleLayout implements HLayout {

    private int currentIndex;
    private float angleStep, startAngle;
    private float angleStepRad, startAngleRad;
    private float radius, startX, startY, startZ;
    private float noiseRadius;
    private boolean rotateTarget;
    private boolean useNoise;

    public HCircleLayout() {
        radius = 100;
        startX = 0;
        startY = 0;
        startZ = 0;
        currentIndex = 0;
        noiseRadius = radius / 10.0F;
        this.angleStep(60.0F);
        this.startAngle(0.0F);
    }

    public PVector startLoc() {
        return new PVector(startX, startY, startZ);
    }

    public HCircleLayout startLoc(float x, float y) {
        startX = x;
        startY = y;
        startZ = 0;
        return this;
    }

    public HCircleLayout startLoc(float x, float y, float z) {
        startX = x;
        startY = y;
        startZ = z;
        return this;
    }

    public float startX() {
        return startX;
    }

    public HCircleLayout startX(float x) {
        startX = x;
        return this;
    }

    public float startY() {
        return startY;
    }

    public HCircleLayout startY(float y) {
        startY = y;
        return this;
    }

    public float startZ() {
        return startZ;
    }

    public HCircleLayout startZ(float z) {
        startZ = z;
        return this;
    }

    public HCircleLayout currentIndex(int i) {
        currentIndex = i;
        return this;
    }

    public int currentIndex() {
        return currentIndex;
    }

    public HCircleLayout resetIndex() {
        currentIndex = 0;
        return this;
    }

    public HCircleLayout radius(float f) {
        radius = f;
        return this;
    }

    public float radius() {
        return radius;
    }

    public HCircleLayout angleStep(float f) {
        angleStepRad = radians(f);
        angleStep = f;
        return this;
    }

    public float angleStep() {
        return angleStep;
    }

    public float angleStepRad() {
        return angleStepRad;
    }

    public HCircleLayout startAngle(float f) {
        startAngleRad = radians(f);
        startAngle = f;
        return this;
    }

    public float startAngle() {
        return startAngle;
    }

    public float startAngleRad() {
        return startAngleRad;
    }

    public HCircleLayout rotateTarget(boolean b) {
        rotateTarget = b;
        return this;
    }

    public boolean rotateTarget() {
        return rotateTarget;
    }


    public HCircleLayout useNoise(boolean b) {
        useNoise = b;
        return this;
    }

    public boolean useNoise() {
        return useNoise;
    }

    public float noiseRadius() {
        return noiseRadius;
    }

    public HCircleLayout noiseRadius(float r) {
        noiseRadius = r;
        return this;
    }

    public float maxRadius() {
        return radius + noiseRadius;
    }

    public float minRadius() {
        return radius - noiseRadius;
    }


    @Override
    public PVector getNextPoint() {

        float a = startAngleRad + (angleStepRad * currentIndex);

        float r = radius;
        if (useNoise) {
            float n = H.app().noise(0.1f * currentIndex);
            n = map(n, 0.0f, 1.0f, -1.0f, 1.0f);
            r = radius + noiseRadius * n;
        }

        float x = r * cos(a) + startX;
        float y = r * sin(a) + startY;
        float z = startZ;

        ++currentIndex;
        return new PVector(x, y, z);
    }

    @Override
    public void applyTo(HDrawable target) {
        if (rotateTarget) {
            float a = radians(90) + startAngleRad + (angleStepRad * currentIndex);
            target.rotationZRad(a);
        }
        target.loc(getNextPoint());
    }
}
 
