package com.md.mdcms.rest.base;

import com.md.mdcms.base.IConstants;
import com.md.mdcms.model.Container;
import com.md.mdcms.rest.model.State;
import com.md.mdcms.xml.Xml;

public class MdTransformer implements IConstants, Xml {

	public static Container transformToGlobalContainer(State state) {
		Container cont = new Container(CONTAINERTYPEGLOBAL);
		cont.addField(ACTION, state.getAction() != null ? state.getAction() : BLANK);
		cont.addField(FUNCTION, state.getFunction() != null ? state.getFunction().toUpperCase() : BLANK);
		cont.addField(LANGID, state.getLangId());
		cont.addField(REQUESTCODE, state.getRequestCode() != null ? state.getRequestCode().toUpperCase() : BLANK);
		cont.addField(SCREEN, state.getScreen() != null ? state.getScreen() : BLANK);
		cont.addField(THREAD, state.getThread() != null ? state.getThread() : BLANK);
		if (state.getAlpha1() != null && !"".equals(state.getAlpha1())) {
			cont.addField(ALPHA1, state.getAlpha1());
		}
		if (state.getAlpha2() != null && !"".equals(state.getAlpha2())) {
			cont.addField(ALPHA2, state.getAlpha2());
		}
		if (state.getNumeric1() != null && !"".equals(state.getNumeric1())) {
			cont.addField(NUMERIC1, state.getNumeric1());
		}
		if (state.getNumeric2() != null && !"".equals(state.getNumeric2())) {
			cont.addField(NUMERIC2, state.getNumeric2());
		}
		return cont;
	}

}
