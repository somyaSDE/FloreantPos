/*
 * OthersView.java
 *
 * Created on August 23, 2006, 12:40 AM
 */

package com.floreantpos.ui.views.order;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.floreantpos.customer.CustomerSelectionDialog;
import com.floreantpos.extension.FloorLayoutPlugin;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.ShopTable;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.ShopTableDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.MiscTicketItemDialog;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.OrderInfoDialog;
import com.floreantpos.ui.views.OrderInfoView;
import com.floreantpos.ui.views.order.actions.ItemSelectionListener;
import com.floreantpos.util.PosGuiUtil;

/**
 * 
 * @author MShahriar
 */
public class OthersView extends JPanel {
	private Ticket currentTicket;
	private ItemSelectionListener itemSelectionListener;

	/** Creates new form OthersView */
	public OthersView() {
		initComponents();
	}

	public OthersView(ItemSelectionListener itemSelectionListener) {
		initComponents();

		setItemSelectionListener(itemSelectionListener);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonPanel = new JPanel();
		btnOrderInfo = new com.floreantpos.swing.PosButton();
		// btnMisc = new com.floreantpos.swing.PosButton();
		// btnGuestNo = new com.floreantpos.swing.PosButton();
		// btnTableNumber = new com.floreantpos.swing.PosButton();

		setBorder(javax.swing.BorderFactory.createTitledBorder(null, "=", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
		setLayout(new BorderLayout());

		// btnSearchItem = new PosButton("MANAGER MENU");
		// btnSearchItem.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// //searchItem();
		// POSMessageDialog.showMessage("Manager approval TBD");
		// }
		// });
		// buttonPanel.add(btnSearchItem);

		buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonPanel.setLayout(new java.awt.GridLayout(1, 0, 5, 5));

		btnOrderInfo.setText(com.floreantpos.POSConstants.ORDER_INFO);
		btnOrderInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doViewOrderInfo();
			}
		});
		buttonPanel.add(btnOrderInfo);

		btnCustomer = new PosButton("CUSTOMER");
		btnCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doAddEditCustomer();
			}
		});
		buttonPanel.add(btnCustomer);

		// btnMisc.setText(com.floreantpos.POSConstants.MISC);
		// btnMisc.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// doInsertMisc(evt);
		// }
		// });
		// buttonPanel.add(btnMisc);

		// btnGuestNo.setText(com.floreantpos.POSConstants.CUSTOMER);
		// btnGuestNo.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// btnCustomerNumberActionPerformed(evt);
		// }
		// });
		// buttonPanel.add(btnGuestNo);
		//
		// btnTableNumber.setText(com.floreantpos.POSConstants.TABLE);
		// btnTableNumber.addActionListener(new java.awt.event.ActionListener()
		// {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// btnTableNumberActionPerformed(evt);
		// }
		// });
		// buttonPanel.add(btnTableNumber);

		add(buttonPanel);
	}// </editor-fold>//GEN-END:initComponents

	protected void doAddEditCustomer() {
		CustomerSelectionDialog dialog = new CustomerSelectionDialog(getCurrentTicket());
		dialog.setSize(800, 650);
		dialog.open();
		OrderView.getInstance().getTicketView().updateView();
	}

	private void doInsertMisc(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doInsertMisc
		MiscTicketItemDialog dialog = new MiscTicketItemDialog(Application.getPosWindow(), true);
		dialog.open();
		if (!dialog.isCanceled()) {
			TicketItem ticketItem = dialog.getTicketItem();
			RootView.getInstance().getOrderView().getTicketView().addTicketItem(ticketItem);
		}
	}// GEN-LAST:event_doInsertMisc

	private void doViewOrderInfo() {// GEN-FIRST:event_btnOrderInfoActionPerformed
		try {
			Ticket ticket = getCurrentTicket();

			List<Ticket> tickets = new ArrayList<Ticket>();
			tickets.add(ticket);

			OrderInfoView view = new OrderInfoView(tickets);
			OrderInfoDialog dialog = new OrderInfoDialog(view);
			dialog.setSize(400, 600);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(Application.getPosWindow());
			dialog.setVisible(true);

		} catch (Exception e) {
			// TODO: handle exception
		}
		// TicketDetailDialog dialog = new
		// TicketDetailDialog(Application.getPosWindow(), true);
		// dialog.setTicket(getCurrentTicket());
		// dialog.open();
		//
		// if(!dialog.isCanceled()) {
		// OrderView.getInstance().getTicketView().updateView();
		// }

	}// GEN-LAST:event_btnOrderInfoActionPerformed

	private void btnCustomerNumberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCustomerNumberActionPerformed
		updateGuestNumber();
	}// GEN-LAST:event_btnCustomerNumberActionPerformed

	private void updateGuestNumber() {
		Ticket thisTicket = getCurrentTicket();
		int guestNumber = thisTicket.getNumberOfGuests();

		NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
		dialog.setTitle(com.floreantpos.POSConstants.NUMBER_OF_GUESTS);
		dialog.setValue(guestNumber);
		dialog.pack();
		dialog.open();

		if (dialog.isCanceled()) {
			return;
		}

		guestNumber = (int) dialog.getValue();
		if (guestNumber == 0) {
			POSMessageDialog.showError(Application.getPosWindow(), com.floreantpos.POSConstants.GUEST_NUMBER_CANNOT_BE_0);
			return;
		}

		thisTicket.setNumberOfGuests(guestNumber);
		updateView();

	}

	private void btnTableNumberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTableNumberActionPerformed
		updateTableNumber();
	}// GEN-LAST:event_btnTableNumberActionPerformed

	private void updateTableNumber() {
		Session session = null;
		Transaction transaction = null;

		try {

			Ticket thisTicket = getCurrentTicket();

			FloorLayoutPlugin floorLayoutPlugin = Application.getPluginManager().getPlugin(FloorLayoutPlugin.class);
			List<ShopTable> tables = null;

			if (floorLayoutPlugin != null) {
				tables = floorLayoutPlugin.captureTableNumbers(thisTicket);
			} else {
				tables = PosGuiUtil.captureTable(thisTicket);
			}

			if (tables == null) {
				return;
			}

			session = TicketDAO.getInstance().createNewSession();
			transaction = session.beginTransaction();

			clearShopTable(session, thisTicket);
			session.saveOrUpdate(thisTicket);

			for (ShopTable shopTable : tables) {
				shopTable.setOccupied(true);
				session.merge(shopTable);

				thisTicket.addTable(shopTable.getTableNumber());
			}

			session.merge(thisTicket);
			transaction.commit();

			updateView();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private void clearShopTable(Session session, Ticket thisTicket) {
		List<ShopTable> tables2 = ShopTableDAO.getInstance().getTables(thisTicket);

		if (tables2 == null)
			return;

		for (ShopTable shopTable : tables2) {
			shopTable.setOccupied(false);
			shopTable.setBooked(false);

			session.saveOrUpdate(shopTable);
		}

		tables2.clear();
	}

	// private com.floreantpos.swing.PosButton btnGuestNo;
	// private com.floreantpos.swing.PosButton btnMisc;
	private com.floreantpos.swing.PosButton btnOrderInfo;
	// private com.floreantpos.swing.PosButton btnTableNumber;
	private com.floreantpos.swing.PosButton btnCustomer;
	// private com.floreantpos.swing.PosButton btnSearchItem;
	private JPanel buttonPanel;

	// End of variables declaration//GEN-END:variables

	public void updateView() {
		// if (currentTicket != null) {
		// if (currentTicket.getType() != OrderType.DINE_IN) {
		// btnGuestNo.setText("");
		// btnTableNumber.setText("");
		//
		// btnGuestNo.setEnabled(false);
		// btnTableNumber.setEnabled(false);
		// } else {
		// btnGuestNo.setEnabled(true);
		// btnTableNumber.setEnabled(true);
		//
		// btnGuestNo.setText(currentTicket.getNumberOfGuests() + " " +
		// POSConstants.GUEST + "s");
		// btnTableNumber.setText(POSConstants.RECEIPT_REPORT_TABLE_NO_LABEL +
		// ": " + currentTicket.getTableNumbers());
		// }
		// }
	}

	public Ticket getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(Ticket currentTicket) {
		this.currentTicket = currentTicket;
		updateView();
	}

	public ItemSelectionListener getItemSelectionListener() {
		return itemSelectionListener;
	}

	public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
		this.itemSelectionListener = itemSelectionListener;
	}

	public void searchItem() {
		int itemId = NumberSelectionDialog2.takeIntInput("Enter or scan item id");

		if (itemId == -1) {
			return;
		}

		MenuItem menuItem = MenuItemDAO.getInstance().get(itemId);
		if (menuItem == null) {
			POSMessageDialog.showError("Item not found");
			return;
		}
		itemSelectionListener.itemSelected(menuItem);
	}
}
