package com.vaadin.tutorial.addressbook;

import com.data.ImageDataDAO;
import com.data.ImageDataModel;
import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * UI class is the starting point for your app. You may deploy it with VaadinServlet
 * or VaadinPortlet by giving your UI class name a parameter. When you browse to your
 * app a web page showing your UI is automatically generated. Or you may choose to
 * embed your UI to an existing web page.
 */
@Title("Addressbook")
@Theme("valo")
public class ImageDataView extends UI implements Button.ClickListener {

	VerticalLayout verticalLayout = new VerticalLayout();
	AbsoluteLayout absoluteLayout = new AbsoluteLayout();
	private Button firstPage;
	private Button previousPage;
	private Button nextPage;
	private Button lastPage;
	private Label showNo;

	PagedTable pagedTable = new PagedTable("This is Paged Table");

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		initLayout();
	}

	private void initLayout() {

		ImageDataDAO dataDAO = new ImageDataDAO();
		ImageDataModel datModel = dataDAO.getImageData(0,1);

		pagedTable.setWidth("1000px");
		pagedTable.setPageLength(25);
		pagedTable.setImmediate(true);
		pagedTable.setSelectable(true);
		pagedTable.setAlwaysRecalculateColumnWidths(true);

		int tableSize = datModel.getImgPageIdList().size();

		System.out.println("tableSize is "+tableSize);
		for(int i=0;i<tableSize;i++){
		getVerticalLayoutData(verticalLayout, datModel, i);
		}

		buildButtonsLayout(verticalLayout);
	}

	private VerticalLayout buildButtonsLayout(final VerticalLayout verticalLayout){

		firstPage = new Button("FIRST PAGE");
		firstPage.addListener(this);
		previousPage = new Button("PREVIOUS PAGE");
		previousPage.addListener(this);
		nextPage = new Button("NEXT PAGE");
		nextPage.addListener(this);
		lastPage = new Button("LAST PAGE");
		lastPage.addListener(this);

		firstPage.setStyleName(BaseTheme.BUTTON_LINK);
		previousPage.setStyleName(BaseTheme.BUTTON_LINK);
		nextPage.setStyleName(BaseTheme.BUTTON_LINK);
		lastPage.setStyleName(BaseTheme.BUTTON_LINK);

		showNo = new Label();

		verticalLayout.addComponent(firstPage);
		verticalLayout.addComponent(previousPage);
		verticalLayout.addComponent(nextPage);
		verticalLayout.addComponent(lastPage);

		return verticalLayout;
	}

	private void loadPage(int pageNum) {

		rebuildPagination();
		// 将PageNum设置在符合条件的合适页数，并更新Pagination当前页
		pageNum = pageNum < 1 ? 1 : pageNum;
		pageNum = pageNum > pagedTable.getTotalAmountOfPages() ? pagedTable.getTotalAmountOfPages() : pageNum;
		pagedTable.setCurrentPage(pageNum);
		//pagination.setCurrentPage(pageNum);

		// 重新设置数据源
//		List<T> entitys = flipSupportService.loadPageEntities(
//				pagination.getStartIndex(), pagination.getPageRecords(),
//				searchSql);
//		entityContainer.removeAllItems();
//		entityContainer.addAll(entitys);

		// 更新按钮状态
		updateButtonsStatus();
	}

	private void rebuildPagination() {
		System.out.println("Inside rebuildPagination");
		if (pagedTable == null) {
			//pagedTable = new Pagination(flipSupportService.getEntityCount(countSql));
		} else {
			//pagination.setTotalRecord(flipSupportService.getEntityCount(countSql));
		}
	}

	// 更新按钮及记录状态
	private void updateButtonsStatus() {

		System.out.println("Inside updateButtons Status");
		firstPage.setEnabled(true);
		previousPage.setEnabled(true);
		nextPage.setEnabled(true);
		lastPage.setEnabled(true);

		if (pagedTable.getCurrentPage() == 1) {
			firstPage.setEnabled(false);
			previousPage.setEnabled(false);
		}

		if (pagedTable.getCurrentPage() == pagedTable.getTotalAmountOfPages()) {
			nextPage.setEnabled(false);
			lastPage.setEnabled(false);
		}
		showNo.setValue(pagedTable.getCurrentPage() + "/"
				+ pagedTable.getTotalAmountOfPages() + "页" + "--总记录数：");
	}

	public void refreshToFirstPage() {
		loadPage(1);
	}

	@Override
	public void buttonClick(Button.ClickEvent event) {

		if (event.getButton() == firstPage) {
			refreshToFirstPage();
		} else if (event.getButton() == previousPage) {
			loadPage(pagedTable.getCurrentPage() - 1);
		} else if (event.getButton() == nextPage) {
			loadPage(pagedTable.getCurrentPage() + 1);
		} else if (event.getButton() == lastPage) {

			// 可能有小Bug，解决方式是刷新Pagination组件，然后取最大页
			loadPage(pagedTable.getTotalAmountOfPages());
		}
	}

	private void getVerticalLayoutData(VerticalLayout verticalLayout,ImageDataModel datModel,int i) {

		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);

		Label label = new Label();
		label.setValue("Case No "+i);

		verticalLayout.addComponent(label);

		verticalLayout.addComponent(getArticleTitle(datModel,i));

		getCaseImage(datModel,i);

		List<Image> imageList = getCaseImage(datModel,i);

		if(imageList!=null && imageList.size()!=0){
		for (int j = 0; j < imageList.size(); j++) {
			verticalLayout.addComponent(displayImage(imageList.get(j)));
		}
		}
		//verticalLayout.addComponent(displayImage(getCaseImage(datModel,i)));
//
//		// Put the component in a D&D wrapper and allow dragging it
//		final DragAndDropWrapper buttonWrap = new DragAndDropWrapper(getTextArea(datModel,i));
//		buttonWrap.setDragStartMode(DragAndDropWrapper.DragStartMode.COMPONENT);
//
//		// Set the wrapper to wrap tightly around the component
//		buttonWrap.setSizeUndefined();
//
//		absoluteLayout.addComponent(buttonWrap);
		verticalLayout.addComponent(getTextArea(datModel,i));
		verticalLayout.setSizeUndefined();
		verticalLayout.setExpandRatio(label,0.2f);
		setContent(verticalLayout);
		//verticalLayout.addComponent(pagedTable);
	}

	private Component getTextArea(ImageDataModel dataModel,int i) {


		RichTextArea textArea = new RichTextArea();
		String textAreaVal = dataModel.getTextDataList().get(i);
		textArea.setValue(textAreaVal);
		textArea.setSizeFull();
		return textArea;
	}

	private Component getArticleTitle(ImageDataModel dataModel,int i) {

		Label articleTitle = new Label();
		articleTitle.setValue(dataModel.getArticleTitleList().get(i));
		articleTitle.setSizeUndefined();
		return articleTitle;
	}

	private DragAndDropWrapper displayImage(Image image) {

		// The panel will give it scrollbars.
		Panel panel = new Panel("Scroll");
		panel.setWidth(70,Unit.PERCENTAGE);
		panel.setHeight(80, Unit.PERCENTAGE);
		panel.setContent(image);

		final DragAndDropWrapper dndWrapper = new DragAndDropWrapper(panel);

		dndWrapper.setDropHandler(new DropHandler()  {

			public void drop(DragAndDropEvent event)  {
				//Do whatever you want when something is dropped
				System.out.println("Do whatever you want when something is dropped");

				final Transferable transferable = event.getTransferable();
				final Component sourceComponent = transferable.getSourceComponent();

				System.out.println("source comp is "+sourceComponent);

				final TargetDetails dropTargetData = event.getTargetDetails();

				System.out.println("dropTarget is "+dropTargetData.getTarget());

				DragAndDropWrapper.WrapperTargetDetails details =
						(DragAndDropWrapper.WrapperTargetDetails) event.getTargetDetails();


				// Calculate the drag coordinate difference
				int xChange = details.getMouseEvent().getClientX();
						//- t.getMouseDownEvent().getClientX();
				int yChange = details.getMouseEvent().getClientY();
						//- t.getMouseDownEvent().getClientY();

				System.out.println("Xchange is "+xChange);
				System.out.println("Ychange is "+yChange);
			}

			//Criterio de aceptacion
			public AcceptCriterion getAcceptCriterion() {
				System.out.println("Inside criterion accept");
				return AcceptAll.get();
			}
		});

// Show exactly the currently contained rows (items)
		return dndWrapper;
	}

	private List<Image> getCaseImage(ImageDataModel dataModel,int i){

		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		int pageID= dataModel.getImgPageIdList().get(i);
		final String FILE_DIR = "/WEB-INF/classes/images/"+pageID+"";
		final String FILE_TEXT_EXT = ".jpg";


		List<Image> imageList = new ArrayList<Image>();

		File filePath = new File(basepath + "/WEB-INF/classes/images/"+pageID+"/");

		if(filePath.isDirectory()==false){
			System.out.println("Directory does not exists : " + FILE_DIR);
			return null;
		}

		System.out.println("files in the path are "+filePath.listFiles());
		System.out.println("files in the path are "+filePath.list());

		String [] fileList = filePath.list();

		String path = "";
		for (String file : fileList) {
			path = new StringBuffer(FILE_DIR).append(File.separator)
					.append(file).toString();
			System.out.println("file : " + path);
			File newFilePath = new File(basepath+path);

			// Image as a file resource
			FileResource resource = new FileResource(newFilePath);

			System.out.println("Resource---"+resource);
			System.out.println("Resourceval is -------"+resource.getSourceFile());

			Image image = new Image("Image No", resource);
			image.setSizeUndefined();
			imageList.add(image);
		}
		return imageList;
	}

	private Image getCaseImage1(){

		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();

		// Image as a file resource
		FileResource resource = new FileResource(
				new File(
						basepath
								+ "/WEB-INF/classes/images/186/752.jpg"));
		Image image = new Image("Image No 146", resource);

		return image;
	}
}
