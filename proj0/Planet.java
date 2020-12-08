public class Planet {
    public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
    }
    
    public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double dx = this.xxPos - p.xxPos;
		double dy = this.yyPos - p.yyPos;
		double distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}

	public double calcForceExertedBy(Planet p) {
		double F = G * this.mass * p.mass / (this.calcDistance(p)*this.calcDistance(p));
		return F;
	}

	public double calcForceExertedByX(Planet p) {
		double Fx = this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
		return Fx;
	}

	public double calcForceExertedByY(Planet p) {
		double Fy = this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
		return Fy;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double F_net_x = 0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (!this.equals(allPlanets[i])) {
				F_net_x += this.calcForceExertedByX(allPlanets[i]);
			}
		}
		return F_net_x;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double F_net_y = 0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (!this.equals(allPlanets[i])) {
				F_net_y += this.calcForceExertedByY(allPlanets[i]);
			}
		}
		return F_net_y;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass; 
		double aY = fY / this.mass; 
		this.xxVel = this.xxVel + dt * aX;
		this.yyVel = this.yyVel + dt * aY;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	//Drawing One Planet
	public void draw() {
		String filename = "images/" + imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, filename);
	}
}