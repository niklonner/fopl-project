package impl.parser;

import java.util.List;
import java.util.ArrayList;
import bnfc.Play.Absyn.*;

public class PlayerParser {
    public List<String, Object> parse(String path) {
    }

    private class Parser implements Visitor {
        public void visitProg(Play.Absyn.Prog prog) {} //abstract class
        public void visitProgram(Play.Absyn.Program program) {
            /* Code For Program Goes Here */

            if (program.listplayer_ != null) {program.listplayer_.accept(this);}
        }
        public void visitListPlayer(Play.Absyn.ListPlayer listplayer) {
            while(listplayer!= null)
            {
                /* Code For ListPlayer Goes Here */
                listplayer.player_.accept(this);
                listplayer = listplayer.listplayer_;
            }
        }
        public void visitPlayer(Play.Absyn.Player player) {
            /* Code For Player Goes Here */

            visitIdent(player.ident_);
            if (player.listattr_ != null) {player.listattr_.accept(this);}
        }
        public void visitListAttr(Play.Absyn.ListAttr listattr) {
            while(listattr!= null)
            {
                /* Code For ListAttr Goes Here */
                listattr.attr_.accept(this);
                listattr = listattr.listattr_;
            }
        }
        public void visitAttr(Play.Absyn.Attr attr) {} //abstract class
        public void visitAttribute(Play.Absyn.Attribute attribute) {
            /* Code For Attribute Goes Here */

            visitIdent(attribute.ident_);
        }
        public void visitValueAttribute(Play.Absyn.ValueAttribute valueattribute) {
            /* Code For ValueAttribute Goes Here */

            visitIdent(valueattribute.ident_);
            valueattribute.val_.accept(this);
        }
        public void visitVal(Play.Absyn.Val val) {} //abstract class
        public void visitIntegerValue(Play.Absyn.IntegerValue integervalue) {
            /* Code For IntegerValue Goes Here */

            visitInteger(integervalue.integer_);
        }
        public void visitDoubleValue(Play.Absyn.DoubleValue doublevalue) {
            /* Code For DoubleValue Goes Here */

            visitDouble(doublevalue.double_);
        }
        public void visitStringValue(Play.Absyn.StringValue stringvalue) {
            /* Code For StringValue Goes Here */

            visitString(stringvalue.string_);
        }
        public void visitIdent(String i) {}
        public void visitInteger(Integer i) {}
        public void visitDouble(Double d) {}
        public void visitChar(Character c) {}
        public void visitString(String s) {}
    }
}
