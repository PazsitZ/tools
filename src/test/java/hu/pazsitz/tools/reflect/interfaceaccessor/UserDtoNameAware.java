package hu.pazsitz.tools.reflect.interfaceaccessor;

import hu.pazsitz.tools.reflect.InterfaceAccessor;

public interface UserDtoNameAware {

	public InterfaceAccessor<UserDtoNameAware, String> ACCESSOR = new InterfaceAccessor<UserDtoNameAware, String>() {
		@Override
		protected String getByInterface(UserDtoNameAware object) {
			return object.getName();
		}
		
	};

	public String getName();
}
