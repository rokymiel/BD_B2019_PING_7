PNGS=$(patsubst %.dot,%.png,$(wildcard *.dot))

all: $(PNGS)

%.png: %.dot
	dot "$<" -o "$@" -Tpng

clean:
	rm -f $(PNGS)
