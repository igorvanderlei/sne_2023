package br.com.sne.sistema.gui.util.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.sne.sistema.teste.SelectedItemListener;


public class AutoCompleteCombo<T> extends JComboBox<T>{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteCombo.class);
	private Model model = new Model();
	private final JTextComponent textComponent = (JTextComponent) getEditor().getEditorComponent();
	private boolean modelFilling = false;
	private boolean updatePopup;
	private List<SelectedItemListener<T>> listener = new ArrayList<SelectedItemListener<T>>();

	public void addListener(SelectedItemListener<T> ltn){
		listener.add(ltn);
	}

	@SuppressWarnings("unchecked")
	public AutoCompleteCombo() {
		setEditable(true);
		setPattern(null);
		updatePopup = false;
		textComponent.setDocument(new AutoCompleteDocument());
		setModel(model);
		setSelectedItem(null);

		new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (updatePopup && isDisplayable()) {
					System.out.println("Atualiza Popup");
					setPopupVisible(false);
					if (model.getSize() > 0) {
						setPopupVisible(true);
					}
					updatePopup = false;
				}
			}
		}).start();
	}

	public void setData(Collection<T> data){
		model.clear();
		for(T t: data)
			model.data.add(t);
	}

	private class AutoCompleteDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;
		boolean arrowKeyPressed = false;

		public AutoCompleteDocument() {
			textComponent.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						System.out.println("[key listener] enter key pressed");
						//there is no such element in the model for now
						String text = textComponent.getText();
						if (!model.data.contains(text)) {
							System.out.println("addToTop() called from keyPressed()");
							addToTop(text);
						} else{
							T selected = model.data.getObject(text);
							for(SelectedItemListener<T> sil: listener)
								sil.itemSelected(selected);							
						}
					} else if (key == KeyEvent.VK_UP ||
							key == KeyEvent.VK_DOWN) {
						arrowKeyPressed = true;
						System.out.println("arrow key pressed");
					} else {
						arrowKeyPressed = false;
					}
				}
			});
		}

		void updateModel() throws BadLocationException {
			String textToMatch = getText(0, getLength());
			System.out.println("setPattern() called from updateModel()");
			if(!arrowKeyPressed)
				setPattern(textToMatch);
		}

		@Override
		public void remove(int offs, int len) throws BadLocationException {
			System.out.println("<REMOVE>");
			if (modelFilling) {
				System.out.println("[remove] model is being filled now");
				return;
			}

			super.remove(offs, len);
			if (arrowKeyPressed) {
				//arrowKeyPressed = false;
				System.out.println("[remove] arrow key was pressed, updateModel() was NOT called");
			} else {
				System.out.println("[remove] calling updateModel()");
				updateModel();
			}
			clearSelection();
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			System.out.println("<INSERT>");
			if (modelFilling) {
				System.out.println("[insert] model is being filled now");
				return;
			}

			super.insertString(offs, str, a);

			String text = getText(0, getLength());
			if (arrowKeyPressed) {
				System.out.println("[insert] arrow key was pressed, updateModel() was NOT called");
				if(model.data.list.containsKey(text))
					model.setSelectedItem( model.data.list.get(text));
				else
					updateModel();
				System.out.println( String.format("[insert] model.setSelectedItem(%s)", text) );
				arrowKeyPressed = false;
			} else if(!text.equals(getSelectedItem())){
				System.out.println("[insert] calling updateModel()");
				updateModel();
			}

			clearSelection();
		}

	}

	public void setText(String text) {
		System.out.println("Set Text");
		if (model.data.contains(text)) {
			setSelectedItem(text);
		} else {
			addToTop(text);
			setSelectedIndex(0);
		}
	}

	public String getText() {
		return getEditor().getItem().toString();
	}

	private String previousPattern = null;

	private void setPattern(String pattern) {

		if(pattern!=null && pattern.trim().isEmpty())
			pattern = null;

		if(previousPattern==null && pattern ==null ||
				pattern!=null && pattern.equals(previousPattern)) {
			System.out.println("[setPatter] pattern is the same as previous: "+previousPattern);
			return;
		}

		previousPattern = pattern;

		modelFilling = true;
		model.setPattern(pattern);

		if(true) {
			StringBuilder b = new StringBuilder(100);
			b.append("pattern filter '").append(pattern==null ? "null" : pattern).append("' set:\n");
			for(int i=0; i<model.getSize(); i++) {
				b.append(", ").append('[').append(model.getElementAt(i)).append(']');
			}
			int ind = b.indexOf(", ");
			if(ind != -1) {
				b.delete(ind, ind+2);
			}
			System.out.println(b);
		}
		modelFilling = false;
		updatePopup = true;
	}


	private void clearSelection() {
		int i = getText().length();
		textComponent.setSelectionStart(i);
		textComponent.setSelectionEnd(i);
	}

	public synchronized void addToTop(String aString) {
		model.addToTop(aString);
	}

	@SuppressWarnings("rawtypes")
	private class Model extends AbstractListModel implements ComboBoxModel {
		private static final long serialVersionUID = 1L;
		T selected;
		final int limit = 20;

		class Data {
			private Map<String, T> list = new HashMap<String, T>(limit);
			private List<String> lowercase = new ArrayList<String>(limit);
			private List<T> filtered;

			void add(T s) {
				list.put(s.toString().toLowerCase(), s);
				lowercase.add(s.toString().toLowerCase());
			}

			@SuppressWarnings("unlikely-arg-type")
			void remove(int index) {
				list.remove(index);
				lowercase.remove(index);
			}

			List<T> getFiltered() {
				if(filtered==null)
					filtered = new ArrayList<T>(list.values());
				return filtered;
			}

			int size() {
				return list.size();
			}

			void setPattern(String pattern) {
				if (pattern == null || pattern.isEmpty()) {
					filtered = new ArrayList<T>(list.values());
					AutoCompleteCombo.this.setSelectedItem(model.getElementAt(0));
					System.out.println( String.format("[setPattern] combo.setSelectedItem(null)") );
				} else {
					filtered = new ArrayList<T>(limit);
					pattern = pattern.toLowerCase();
					for(int i=0; i<lowercase.size(); i++) {
						if (lowercase.get(i).contains(pattern)) {
							filtered.add( list.get(lowercase.get(i)) );
						}
					}
					AutoCompleteCombo.this.setSelectedItem(pattern);
					System.out.println( String.format("[setPattern] combo.setSelectedItem(%s)", pattern) );
				}
				System.out.println( String.format("pattern:'%s', filtered: %s", pattern, filtered) );
			}

			boolean contains(String s) {
				if(s==null || s.trim().isEmpty())
					return true;
				return list.get(s.toLowerCase()) != null;
			}
			
			T getObject(String key){
				if(key==null || key.trim().isEmpty())
					return null;
				return list.get(key.toLowerCase());
			}
		}

		Data data = new Data();

		public Model() {

		}

		public void clear() {
			data.list.clear();
			data.lowercase.clear();
		}

		public void setPattern(String pattern) {
			int size1 = getSize();
			data.setPattern(pattern);
			int size2 = getSize();

			if(size1<size2) {
				fireIntervalAdded(this, size1, size2-1);
				fireContentsChanged(this, 0, size1-1);
			} else if(size1>size2) {
				fireIntervalRemoved(this, size2, size1-1);
				fireContentsChanged(this, 0, size2-1);
			}
		}

		public void addToTop(String aString) {
			if(aString==null || data.contains(aString))
				return;
			while(data.size()>limit) {
				int index = data.size()-1;
				data.remove(index);
			}

			setPattern(null);
			model.setSelectedItem(aString);
			logger.debug( String.format("[add to top] model.setSelectedItem(%s)", aString) );

		}

		@Override
		public T getSelectedItem() {
			return selected;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setSelectedItem(Object anObject) {
			if ((selected != null && !selected.equals(anObject)) ||
					selected == null && anObject != null) {
				selected = (T) anObject;
				fireContentsChanged(this, -1, -1);
			}
		}

		@Override
		public int getSize() {
			return data.getFiltered().size();
		}

		@Override
		public T getElementAt(int index) {
			return data.getFiltered().get(index);
		}

	}
}
