package nl.cwi.examples.ql;

import nl.cwi.examples.ql.schema.BinOp;
import nl.cwi.examples.ql.schema.Block;
import nl.cwi.examples.ql.schema.Computed;
import nl.cwi.examples.ql.schema.Expr;
import nl.cwi.examples.ql.schema.Form;
import nl.cwi.examples.ql.schema.IfThen;
import nl.cwi.examples.ql.schema.IfThenElse;
import nl.cwi.examples.ql.schema.Lit;
import nl.cwi.examples.ql.schema.QL;
import nl.cwi.examples.ql.schema.QLPrimitives;
import nl.cwi.examples.ql.schema.Question;
import nl.cwi.examples.ql.schema.Statement;
import nl.cwi.examples.ql.schema.Var;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.data_manager.IDataManager;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.schema.models.definition.Type;

public class QLExample {
	   public static void main(String[] args) {
	        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

	        final Schema qlSchema =
	                SchemaLoader.load(
	                    schemaFactory,
	                    BinOp.class, Block.class, Computed.class, Expr.class, Form.class,
	                    IfThen.class, IfThenElse.class, Lit.class, Question.class, Statement.class, Var.class, QLPrimitives.class);

	        final IDataManager dataManager = new BasicDataManager();
	        final QL qlFactory =
	                dataManager.factory(QL.class, qlSchema);
	        Form tax = tax(qlFactory);
	        
	        for (Statement s: tax.body()) {
	        	//if (s instanceof Question) {
	        	if (s.schemaKlass().name().equals("Question")) {
	        		System.out.println(s.schemaKlass().name());
	        		System.out.println(((Question)s).label());
	        		System.out.println(((Question)s).name());
	        	}
	        }
	        
	        for (Type k: qlSchema.types()) {
	        	System.out.println(k.name());
	        }
	    }
	   
	   public static Form tax(QL f) {
		   Form form = f.Form();
		   form.name("Box1HouseOwning");
		   
		   Question hasSoldHouse = f.Question();
		   hasSoldHouse.name("hasSoldHouse");
		   hasSoldHouse.label("Did you sell a house?");
		   hasSoldHouse.type(Question.Type.BOOL);
		   
		   Question hasBoughtHouse = f.Question();
		   hasBoughtHouse.name("hasBoughtHouse");
		   hasBoughtHouse.label("Did you buy a house");
		   hasBoughtHouse.type(Question.Type.BOOL);
		   
		   Question hasMaintLoan = f.Question();
		   hasMaintLoan.name("hasMaintLoan");
		   hasMaintLoan.label("Did you enter a loan?");
		   hasMaintLoan.type(Question.Type.BOOL);
		   
		   
		   Question sellingPrice = f.Question();
		   sellingPrice.name("sellingPrice");
		   sellingPrice.label("What was the selling price?");
		   Question privateDebt = f.Question();
		   privateDebt.name("privateDebt");
		   privateDebt.label("What was the private debt?");
		   Computed valueResidue = f.Computed();
		   valueResidue.name("valueResidue");
		   valueResidue.label("Value residue:");
		   Var x = f.Var(); x.ref(sellingPrice);
		   Var y = f.Var(); y.ref(privateDebt);
		   BinOp sub = f.BinOp();
		   sub.lhs(x);
		   sub.rhs(y);
		   valueResidue.expr(sub);
		   
		   Block block2 = f.Block();
		   block2.body().add(sellingPrice);
		   block2.body().add(privateDebt);
		   block2.body().add(valueResidue);
		   
		   
		   Var c = f.Var();
		   c.ref(hasSoldHouse);
		   
		   IfThen ifThen = f.IfThen();
		   ifThen.condition(c);
		   ifThen.then(block2);
		   
		   form.body().add(hasSoldHouse);
		   form.body().add(hasBoughtHouse);
		   form.body().add(hasMaintLoan);
		   form.body().add(ifThen);
		   
		   
		   return form;
	   }
}
