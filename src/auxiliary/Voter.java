package auxiliary;

//无需修改本代码
public class Voter {
	
	//只需保留身份ID即可
	private String ID;

	public Voter(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Voter))
			return false;
		Voter other = (Voter) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}
}
