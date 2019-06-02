import javax.swing.JLabel;

/**
 * @author abhin Class whose objects are used to store each image block along
 *         with its original position
 */
public class SubI {
	JLabel lab;
	int loc;

	/**
	 * @return label value
	 */
	public JLabel getLab() {
		return lab;
	}

	/**
	 * @param lab label value
	 * @param loc location in grid index form parameterized construction
	 */
	public SubI(JLabel lab, int loc) {
		super();
		this.lab = lab;
		this.loc = loc;
	}

	/**
	 * @param lab label value
	 */
	public void setLab(JLabel lab) {
		this.lab = lab;
	}

	/**
	 * default constructor
	 */
	public SubI() {
		super();
	}

	/**
	 * @return location in grid index
	 */
	public int getLoc() {
		return loc;
	}

	/**
	 * @param loc location in grid index
	 */
	public void setLoc(int loc) {
		this.loc = loc;
	}

}
