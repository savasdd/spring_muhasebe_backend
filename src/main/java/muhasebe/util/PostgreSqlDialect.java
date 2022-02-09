package muhasebe.util;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class PostgreSqlDialect extends PostgreSQL94Dialect {

	@Override
	public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
		switch (sqlTypeDescriptor.getSqlType()) {
		case Types.CLOB:
			return VarcharTypeDescriptor.INSTANCE;
		case Types.BLOB:
			return VarcharTypeDescriptor.INSTANCE;
		case 1111:// 1111 should be json of pgsql
			return VarcharTypeDescriptor.INSTANCE;
		}
		return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
	}

	public PostgreSqlDialect() {
		super();
		registerHibernateType(1111, "string");
	}

}
