
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Access(AccessType.PROPERTY)
public class FolderMoveForm {

	private Integer	idNewParent;
	private int		folderToMove;


	public Integer getIdNewParent() {
		return this.idNewParent;
	}

	public void setIdNewParent(final int idNewParent) {
		this.idNewParent = idNewParent;
	}

	public int getFolderToMove() {
		return this.folderToMove;
	}

	public void setFolderToMove(final int folderToMove) {
		this.folderToMove = folderToMove;
	}

}
