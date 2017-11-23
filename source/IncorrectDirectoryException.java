package source;

public class IncorrectDirectoryException extends Exception{ 
	private static final long serialVersionUID = 1L;
	private String dir;
	public IncorrectDirectoryException(String dir){
		this.dir = dir;
	}
	public String getMessage(){
		return dir+" does not exist, or exists outsde the democratic world!";
	}
}
