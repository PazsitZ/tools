package hu.pazsitz.tools.interfaceaccessor;

import hu.pazsitz.tools.InterfaceAccessor;

public interface UserDtoNameAware {

	public InterfaceAccessor<UserDtoNameAware, String> ACCESSOR = new InterfaceAccessor<UserDtoNameAware, String>() {
		@Override
		protected String getByInterface(UserDtoNameAware object) {
			return object.getName();
		}
		
	};

	public String getName();
}
