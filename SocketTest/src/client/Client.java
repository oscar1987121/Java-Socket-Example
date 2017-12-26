package client;

import handle.IMessageReceiver;
import handle.InputHandle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Client extends ApplicationWindow {
	private Text text;
	private Text text_1;

	//Test
	private Socket socket;

	private OutputStream outputStream;
	private PrintWriter pw;

	private InputHandle handle;
	
	//
	/**
	 * Create the application window.
	 */
	public Client() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					socket = new Socket("127.0.0.1", 8001);
					handle = new InputHandle(socket,
							new IMessageReceiver() {

								@Override
								public void receive(final String message) {
									Display.getDefault().asyncExec(
											new Runnable() {
												public void run() {
													String currentText = text
															.getText();
													text.setText(currentText
															+ "\r\n"
															+ message);
												}

											});
								}
							});
					Thread tr = new Thread(handle);
					tr.start();
					
					
					outputStream = socket.getOutputStream();
					pw = new PrintWriter(outputStream);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t.start();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		{
			text = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
					| SWT.V_SCROLL | SWT.MULTI);
			text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
					1));
		}
		{
			text_1 = new Text(container, SWT.BORDER);
			text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false, 1, 1));
		}
		{
			Button btnNewButton = new Button(container, SWT.NONE);
			btnNewButton.setText("    Enter    ");
			btnNewButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (text_1 != null) {
						String message = text_1.getText();
						pw.println("[Client Say]" + message);
						pw.flush();
						text.setText(text.getText() + "\r\n[I said]" + message);
					}

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Client window = new Client();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				handle.close();
				try {
					if (outputStream != null) {
						outputStream.close();
					}
					if (pw != null) {
						pw.close();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		newShell.setText("Client");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(597, 491);
	}

}
