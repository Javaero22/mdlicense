package com.md.mdcms.cms.backingbean;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.access.IFSFile;
import com.md.mdcms.base.ApplicationHelper;

public abstract class MdExportBackingBean extends MdBackingBean {

	/** Log instance for this class. */
	protected static final Log LOG = LogFactory.getLog(MdExportBackingBean.class);

	private static final long serialVersionUID = 1L;

	protected static final String ADDATTBTN = "ADDATT";

	protected MdExportBackingBean() {
		super();
	}

	// public String uploadAttachment(String jobNumber, String as400Folder,
	// UploadedFile uploadedFile, StringField newatt) {
	// String message = null;
	// if (uploadedFile != null) {
	// LOG.info("ATTACHMENT UPLOAD ======>>>>>>>>>> START <<<<<<<<<=========");
	//
	// String uploadFileName = FileUtil.createUploadFileName(uploadedFile
	// .getName());
	//
	// IFSFile ifsFile = null;
	// try {
	// ifsFile = createIFSFile(jobNumber, as400Folder, uploadFileName);
	// } catch (IOException e) {
	// LOG.fatal(e);
	//
	// FacesContext context = FacesContext.getCurrentInstance();
	// MiddlewareException mExc = new MiddlewareException("", "30",
	// e.getLocalizedMessage());
	// ApplicationHelper.getUserBean().addException(mExc);
	// FacesMessage facesMessage = new FacesMessage(
	// mExc.getFacesSeverity(), mExc.getMessage(),
	// mExc.getMessage());
	// context.addMessage(mExc.getId(), facesMessage);
	// }
	// LOG.info("Upload attachment - filePathName: " + ifsFile.getName());
	//
	// if (ifsFile != null) {
	// long startTime = System.currentTimeMillis();
	// LOG.info("Uploading file to AS400");
	// message = FileUtil.uploadFileToAS400(uploadedFile, ifsFile);
	// LOG.info("Upload ended: " + message);
	// long endTime = System.currentTimeMillis();
	// long diffTime = endTime - startTime;
	// LOG.info("Time to upload attachment " + uploadFileName + " ("
	// + uploadedFile.getName() + ")" + " was: " + diffTime
	// + " ms");
	// }
	//
	// if (message == null) {
	// // newatt = new StringField();
	// newatt.setId("NEWATT");
	// newatt.setEditable(FALSE);
	// newatt.setVisible(FALSE);
	// newatt.setHtml(uploadFileName);
	// LOG.info("ATTACHMENT UPLOAD ======>>>>>>>>>> END <<<<<<<<<=========");
	// FacesContext context = FacesContext.getCurrentInstance();
	// MiddlewareException mExc = new MiddlewareException("", "10",
	// "Attachment: " + uploadFileName
	// + " successfully uploaded.");
	// ApplicationHelper.getUserBean().addException(mExc);
	// FacesMessage facesMessage = new FacesMessage(
	// mExc.getFacesSeverity(), mExc.getMessage(),
	// mExc.getMessage());
	// context.addMessage(mExc.getId(), facesMessage);
	// return btnPress(ADDATTBTN);
	// }
	// } else {
	// message = "Please choose file to upload!";
	// }
	//
	// if (message != null) {
	// FacesContext context = FacesContext.getCurrentInstance();
	// MiddlewareException mExc = new MiddlewareException("", "30",
	// message);
	// ApplicationHelper.getUserBean().addException(mExc);
	// FacesMessage facesMessage = new FacesMessage(
	// mExc.getFacesSeverity(), mExc.getMessage(),
	// mExc.getMessage());
	// context.addMessage(mExc.getId(), facesMessage);
	// }
	// return null;
	// }

	private IFSFile createIFSFile(String jobNumber, String as400Folder, String uploadFileName) throws IOException {
		IFSFile ifsFolder = new IFSFile(ApplicationHelper.getUserBean().getUserSessions().get(jobNumber).getAs400(),
				as400Folder);
		if (!ifsFolder.exists()) {
			if (!ifsFolder.mkdirs()) {
				// FacesContext context = FacesContext.getCurrentInstance();
				// MiddlewareException mExc = new MiddlewareException("", "30",
				// "Not possible to create folder structure for storing file
				// attachment");
				// ApplicationHelper.getUserBean().addException(mExc);
				// FacesMessage facesMessage = new FacesMessage(
				// mExc.getFacesSeverity(), mExc.getMessage(),
				// mExc.getMessage());
				// context.addMessage(mExc.getId(), facesMessage);
				return null;
			}
		}

		IFSFile ifsFile = new IFSFile(ApplicationHelper.getUserBean().getUserSessions().get(jobNumber).getAs400(),
				as400Folder, uploadFileName);

		return ifsFile;
	}

	/**
	 * @param folderDirToOpen
	 * @param fileNameToOpen
	 * @return
	 */
	public String openAsAttachment(String jobNumber, String folderDirToOpen, String fileNameToOpen) {
		// FacesContext context = FacesContext.getCurrentInstance();
		// HttpServletResponse response = (HttpServletResponse)
		// context.getExternalContext().getResponse();

		// => file extensions handled via web.xml
		// response.setContentType("application/vnd.ms-excel");

		// String message = FileUtil.streamIFSFileToSOS(jobNumber,
		// folderDirToOpen, fileNameToOpen, response);
		// if (message != null) {
		// context = FacesContext.getCurrentInstance();
		// MiddlewareException mExc = new MiddlewareException("", "30",
		// message);
		// FacesMessage facesMessage = new FacesMessage(mExc.getFacesSeverity(),
		// mExc.getMessage(), mExc.getMessage());
		// context.addMessage(mExc.getId(), facesMessage);
		// context.renderResponse();
		// } else {
		// context.responseComplete();
		// }
		return null;
	}
}
