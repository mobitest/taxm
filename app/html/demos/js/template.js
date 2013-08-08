		//从模板串从替换参数
		slice = Array.prototype.slice;
		function template(source, params) {
			if (arguments.length === 1) {
				return function () {
					var args = slice.call(arguments);
					args.unshift(source);
					return apply(this, args);
				};
			}
			// Detect different call patterns:
			// * template(source, [1, 2, 3])
			// * template(source, 1, 2, 3)
			if (!$.isArray(params)) {
				params = slice.call(arguments, 1);
			}
			$.each(params, function (i, n) {
				source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
			});
			return source;
		}
		//alert(template("pos1: {0} pos2: {1} pos3:{2}",["AA","BB",3]));
		//alert(template("{0}..{1}....", "num1", "num2"));