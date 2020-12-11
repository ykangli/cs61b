public class NBody {
    public static double readRadius(String filename) {
		In in = new In(filename);
		int num_planets = in.readInt();
		double radius =  in.readDouble();
		return radius;
    }
    
    public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int num_planets = in.readInt();
		double radius = in.readDouble();
		Planet[] arr = new Planet[num_planets];
		for (int i = 0; i < arr.length; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			arr[i] = p;
		}
		return arr;
    }
    
    public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = NBody.readPlanets(filename);
		double uniradius = NBody.readRadius(filename);

        //Drawing the Background
		StdDraw.setScale(-uniradius, uniradius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

        //Drawing All of the Planets
		for (int i = 0; i < planets.length; i++) {
			planets[i].draw();
        }

        StdDraw.enableDoubleBuffering();
        double time = 0;
        while(time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.setScale(-uniradius, uniradius);
		    StdDraw.clear();
		    StdDraw.picture(0, 0, "images/starfield.jpg");
            
            for (int i = 0; i < planets.length; i++) {
                planets[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt; 
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", uniradius); 
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);    
        }
	}
}
 